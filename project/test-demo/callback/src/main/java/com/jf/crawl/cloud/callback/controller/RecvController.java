package com.jf.crawl.cloud.callback.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jf.crawl.cloud.callback.dao.CallbackConfigDao;
import com.jf.crawl.cloud.callback.dao.CallbackLogDao;
import com.jf.crawl.cloud.callback.dao.CallbackModelDao;
import com.jf.crawl.cloud.callback.entity.CallbackConfigEntity;
import com.jf.crawl.cloud.callback.entity.CallbackLogEntity;
import com.jf.crawl.cloud.callback.entity.CallbackMessage;
import com.jf.crawl.cloud.callback.entity.CallbackModelEntity;
import com.jf.crawl.cloud.callback.jms.JmsConsumer;
import com.jf.crawl.cloud.callback.service.BizCallbackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author liumg
 * @version 创建时间：2019/7/12.
 */

@RestController
    @RequestMapping("/callback")
public class RecvController {

    private static Logger logger = LoggerFactory.getLogger(RecvController.class);

    @Autowired
    BizCallbackService bizCallbackService;

    @Autowired
    JmsConsumer jmsConsumer;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    CallbackLogDao callbackLogDao;

    @Autowired
    CallbackConfigDao callbackConfigDao;

    @Autowired
    CallbackModelDao callbackModelDao;


    @RequestMapping(value = "recv", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "接收回调相关数据接口", notes = "成功httpStatus返回201，失败返回200", produces = "application/json")
    public void recv(@Validated @RequestBody @ApiParam CallbackMessage messageEntity) {
        long startTimeA = System.currentTimeMillis();
        JSONObject body = JSONObject.parseObject(JSON.toJSONString(messageEntity));
        String notifyEvent = messageEntity.getNotifyEvent();
        String taskId = messageEntity.getTaskId();
        String appId = messageEntity.getAppId();
        String product = messageEntity.getProduct();

        CallbackLogEntity log = new CallbackLogEntity();

        logger.info("当前回调流水号为：{}, 回调参数：{}", taskId, messageEntity);
        log.setTaskKey(taskId);
        log.setNotifyEvent(notifyEvent);
        log.setProduct(product);
        log.setAppId(appId);
        log.setStep("START");
        JSONArray params = constructParams(appId, product, notifyEvent, log);
        if ((null == params) || params.isEmpty()) {
            logger.error("当前回调流水号为：{},failed:{}",taskId, body.toJSONString());
            return;
        }

        JSONObject messageObject = new JSONObject();
        messageObject.put("params", params);
        messageObject.put("biz", JSONObject.toJSONString(messageEntity));
        long startTime = System.currentTimeMillis();
        //发送到mq中
        if(jmsConsumer.sendMessage(messageObject.toJSONString())) {
            logger.info("当前回调流水号为：{},已将数据发送到mq中",taskId);
            httpServletResponse.setStatus(201);
            long cost = System.currentTimeMillis() - startTime;
            logger.info("发送消息耗时:cost={}",cost);
        } else {
            logger.error("当前回调流水号为：{},failed:{}", taskId,body.toJSONString());
        }
        long cost = System.currentTimeMillis() - startTimeA;
       logger.info("接口耗时:cost={}",cost);
    }

    @RequestMapping(value = "shutdown", method = RequestMethod.GET)
    @ApiOperation(value = "关闭mq当前消费者接口")
    public void shutDown() {
        jmsConsumer.setPeedingClose(true);

        try {
            Thread.sleep(3000);
            if (jmsConsumer.isPeedingClose()) {
                httpServletResponse.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private JSONArray constructParams(String appId, String product, String notifyEvent, CallbackLogEntity log) {

        String taskId = log.getTaskKey();
        List<CallbackConfigEntity> configList;
        try {
            //判断是否有回调配置
            configList = callbackConfigDao.selectConfigByAppIdAndNotifyEvent(appId, product, notifyEvent);
            if (null == configList || configList.isEmpty()) {
                logger.info("当前回调流水号为：{},当前任务商户没有回调配置地址, appId={}, product={}, notifyEvent={}", taskId, appId, product, notifyEvent);
                log.setTransNo(UUID.randomUUID().toString());
                log.setStep("NO_CONFIG");
                log.setUpdateTime(new Date());
                callbackLogDao.insertCallbackLog(log);
                return null;
            }

            JSONArray array = new JSONArray();
            //组装参数
            for (CallbackConfigEntity configEntity : configList) {
                String partner = configEntity.getPartner();
                //判断是否有回调模型
                CallbackModelEntity modelEntity = callbackModelDao.selectModelByAppIdAndProduct(appId, product, notifyEvent, partner);
                if (modelEntity == null) {
                    logger.error("当前回调流水号为：{},回调模型为空，appId={}, product={}, notifyEvent={}, partner={}", taskId, appId, product, notifyEvent, partner);
                    log.setStep("NO_MODEL");
                    log.setTransNo(UUID.randomUUID().toString());
                    log.setUpdateTime(new Date());
                    long startTimeA = System.currentTimeMillis();
                    callbackLogDao.insertCallbackLog(log);
                    long cost = System.currentTimeMillis() - startTimeA;
                    logger.info("数据库插入回调流水耗时A:cost={}", cost);
                    continue;
                }

                String transNo = UUID.randomUUID().toString();
                CallbackLogEntity entity = log.copy();
                entity.setPartner(partner);
                entity.setTransNo(transNo);
                entity.setCreateTime(new Date());
                entity.setUpdateTime(new Date());

                String url = configEntity.getUrl();
                JSONObject body = new JSONObject();

                logger.info("当前回调流水号为：{},当前product为:{}", taskId, modelEntity.getProduct());

                body.put("url", url);
                body.put("transNo", transNo);
                body.put("method", modelEntity.getMethod());
                body.put("model", modelEntity.getData());
                body.put("partner", partner);
                long startTimeB = System.currentTimeMillis();
                callbackLogDao.insertCallbackLog(entity);
                long cost = System.currentTimeMillis() - startTimeB;
                logger.info("数据库插入回调流水耗时B:cost={}", cost);
                array.add(body);
            }
            return array;
        }catch (Exception e){
            logger.info("打印异常结果:",e);
        }
        return null;
    }
}
