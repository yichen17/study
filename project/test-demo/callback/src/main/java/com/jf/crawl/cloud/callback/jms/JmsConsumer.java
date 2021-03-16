package com.jf.crawl.cloud.callback.jms;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jf.crawl.cloud.callback.service.BizCallbackService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import javax.jms.Queue;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author liumg
 * @version 创建时间：2019/5/24.
 */

@Component
public class JmsConsumer {

    protected static Logger logger = LoggerFactory.getLogger(JMSConsumer.class);

    @Value("${activemq.username}")
    String username;

    @Value("${activemq.password}")
    String password;

    @Value("${activemq.master.brokerURL}")
    String brokerURL;

    @Value("${activemq.backup.brokerURL}")
    String backupBrokerURL;

    @Value("${runtime.biz.queueNames}")
    String queueNames;

    @Value("${runtime.biz.concurrency}")
    int bizConcurrent;

    @Value("${runtime.biz.env}")
    private String env;

    @Value("${runtime.biz.product}")
    private String product;

    @Autowired
    BizCallbackService bizCallbackService;

    private Map<MessageConsumer, Connection> consumerMap = new HashMap<>(4);

    private Semaphore semaphore;

    private boolean isCloseConnection = false;

    List<Session> seesionList = new ArrayList<>(2);

    ThreadPoolTaskExecutor handleThreadPool;

    ThreadPoolTaskExecutor recvThreadPool;

    boolean isPeedingClose = false;

    Map<String, List<ActiveMQMessageProducer>> producersMap = new HashMap<>(2);
    /**
     * 初始化消息队列
     * @return 成功返回true，失败返回false
     */
    @PostConstruct
    public boolean init() {

        semaphore = new Semaphore(bizConcurrent);
        if (StringUtils.isEmpty(brokerURL) && StringUtils.isEmpty(backupBrokerURL)) {
            logger.error("主备消息队列地址不可以同时为空");
            return false;
        }

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("recv-pool-%d").build();
        recvThreadPool = new ThreadPoolTaskExecutor();
        recvThreadPool.setCorePoolSize(2);
        recvThreadPool.setMaxPoolSize(2);
        recvThreadPool.setQueueCapacity(1024);
        recvThreadPool.setThreadFactory(namedThreadFactory);
        recvThreadPool.initialize();

        ThreadFactory handleFactory = new ThreadFactoryBuilder().setNameFormat("biz-pool-%d").build();
        handleThreadPool = new ThreadPoolTaskExecutor();
        handleThreadPool.setCorePoolSize(20);
        handleThreadPool.setThreadFactory(handleFactory);
        handleThreadPool.setMaxPoolSize(bizConcurrent);
        handleThreadPool.initialize();


        if (!StringUtils.isEmpty(brokerURL)) {
            logger.info("建立主MQ通道的连接，brokerURL={}", brokerURL);
            createConnection(brokerURL);
            initProducers(brokerURL, "master");
        }

        if (!StringUtils.isEmpty(backupBrokerURL)) {
            logger.info("建立备用MQ通道的连接，brokerURL={}", backupBrokerURL);
            createConnection(backupBrokerURL);
            initProducers(brokerURL, "slave");
        }
        return true;
    }

    private boolean createConnection(String brokerURL) {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);

        //预取消息大小设置为0，全部为实时消费，不做消息缓冲
        ActiveMQPrefetchPolicy policy = new ActiveMQPrefetchPolicy();
        policy.setQueuePrefetch(0);

        //设置监听器，监听mq当前状况
        activeMQConnectionFactory.setPrefetchPolicy(policy);
        String clientId = getLocalHostIp() + "-" + env + "-" + product + "-" + RandomStringUtils.randomAlphanumeric(8);

        try {
            logger.info("设置当前连接client id为：{}", clientId);
            Connection connection = activeMQConnectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            seesionList.add(session);

            //无效topic
            if (StringUtils.isEmpty(queueNames)) {
                logger.error("初始化MQ失败，当前runtime.biz.queueNames为空");
                return false;
            }

            for (String queueName : queueNames.split(";")) {
                final Queue queue = session.createQueue(queueName);
                final MessageConsumer consumer = session.createConsumer(queue);
                consumerMap.put(consumer, connection);
            }
            logger.info("建立MQ连接成功");
        } catch (JMSException e) {
            logger.error("启动MQ发生异常，堆栈信息为：", e);
        }

        //启动消息监听器
        return startMessageListener();
    }
    /**
     *  启动MQ监听器
     * @return 成功返回true，失败返回false
     */
    private boolean startMessageListener() {

        //启动MQ连接检测守护线程
        startCheckThread();
        for (MessageConsumer consumer : consumerMap.keySet()) {

            recvThreadPool.execute(() ->{

                while (true) {
                    //如果需要关闭监听器，退出当先接收线程
                    if (isPeedingClose) {
                        logger.info("正在关闭MQ连接，接收线程{}已退出！", Thread.currentThread().getName());
                        break;
                    }

                    //接收消息
                    try {
                        final Message message = consumer.receive();
                        if (null == message) {
                            TimeUnit.MILLISECONDS.sleep(200);
                            continue;
                        }

                        //消息处理
                        semaphore.acquire();
                        String messageId = message.getJMSMessageID();
                        logger.info("开始消费消息MessageID = {}", messageId);
                        handleThreadPool.submit(() -> {
                            try {
                                TextMessage textMessage = (TextMessage) message;
                                bizCallbackService.handle(textMessage.getText());
                                logger.info("完成消息处理，MessageID = {}", messageId);
                            }catch (Exception e1) {
                                logger.error("业务处理消息出现异常，messageId={}", messageId, e1);
                            } finally {
                                semaphore.release();
                            }
                        });
                    } catch (JMSException e) {
                        logger.error("接收消息发生异常，exception=", e);
                    } catch (InterruptedException e1) {
                        logger.error("线程被关闭，线程ID={}", Thread.currentThread().getName());
                    }
                }
            });
        }
        return true;
    }

    /**
     * 启动后台检测mq连接线程
     */
    void startCheckThread() {
        //建立单独销毁线程，解决如果长期无任务无法退出的bug
        Thread t = new Thread(()->{

            Thread.currentThread().setName("check-mq-conn");
            while (true) {
                try {
                    Thread.sleep(3000);

                    //连接关闭，退出循环
                    if (closeConnectionIfAbsent()) {
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private boolean closeConnectionIfAbsent() {

        logger.info("当前线程空闲任务数：{}", semaphore.availablePermits());
        //等到所有的任务接收退出
        if ((semaphore.availablePermits() == bizConcurrent) && isPeedingClose) {

            for (MessageConsumer consumer : consumerMap.keySet()) {
                try {
                    Connection connection = consumerMap.get(consumer);
                    consumer.close();
                    connection.close();
                }catch (Exception e) {
                    logger.error("关闭MQ连接失败，当前异常信息为：", e);
                }
            }

            //关闭线程池，进行资源回收
            isCloseConnection = true;
            handleThreadPool.destroy();
            recvThreadPool.shutdown();
            logger.info("所有的消息已完全处理，MQ连接已成功关闭");
            return true;
        }

        return false;
    }

    /**
     * 发送消息到指定queueName队列中, 优先发送消息到Master队列<br>
     * @return 发送成功返回true，失败返回false
     */
    public boolean sendMessage(String message) {

        //如果已断连接，返回false
        if (isCloseConnection) {
            return false;
        }

        for (String key : producersMap.keySet()) {
            try {
                List<ActiveMQMessageProducer> producers = producersMap.get(key);
                int index = RandomUtils.nextInt(0, 100);
                ActiveMQMessageProducer producer = producers.get(index);
                ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
                textMessage.setText(message);
                long startTime = System.currentTimeMillis();
                producer.send(textMessage,new AsyncCallback() {
                    @Override
                    public void onSuccess() {
                        logger.info( "消费消息MessageID = {},发送成功",textMessage.getJMSMessageID());
                    }

                    @Override
                    public void onException(JMSException exception) {
                        logger.error( "消费消息MessageID = {},发送失败",textMessage.getJMSMessageID(),exception);
                    }
                });
                long cost = System.currentTimeMillis() - startTime;
                logger.info("mq发送耗时:cost={}",cost);
                return true;
            } catch (Exception e) {
                logger.error("发送数据失败，mq节点为{}", key);
            }
        }
        return false;
    }

    private static String getLocalHostIp() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            return ia.getHostAddress();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isPeedingClose() {
        return isPeedingClose;
    }

    public void setPeedingClose(boolean peedingClose) {
        isPeedingClose = peedingClose;
    }

    private void initProducers(String brokerURL, String name) {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
        activeMQConnectionFactory.setUseAsyncSend(true);
        List<ActiveMQMessageProducer> producers = new ArrayList<>(100);
        logger.info("设置当前连接client id为：{}");
        for (int i = 0; i< 100; i++) {
            try {
                Connection connection = activeMQConnectionFactory.createConnection();
                connection.setClientID(UUID.randomUUID().toString());
                connection.start();
                Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
                ActiveMQMessageProducer producer = (ActiveMQMessageProducer)session.createProducer(new ActiveMQQueue(queueNames));
                producers.add(producer);
            }catch (Exception e) {
                logger.error("启动生产者消费者失败");
            }
        }
        producersMap.put(name, producers);
    }
}
