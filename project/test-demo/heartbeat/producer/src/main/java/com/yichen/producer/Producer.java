//package com.yichen.producer;
//
//import com.alibaba.fastjson.JSON;
//import com.yichen.model.Student;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * @author Qiuxinchao
// * @version 1.0
// * @date 2021/8/4 16:33
// * @describe
// */
//@Component
//public class Producer implements InitializingBean, DisposableBean {
//
//    private Thread thread;
//    private volatile boolean isStop=false;
//
//    @Autowired(required=false)
//    private KafkaTemplate<String,String> kafkaTemplate;
//
//    @Override
//    public void destroy() throws Exception {
//        isStop=true;
//        thread.interrupt();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        Student student=new Student();
//        student.setAge("18");
//        student.setName("yichen");
//        student.setIsSettle("0");
//        thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(!isStop){
//                    kafkaTemplate.send("test", JSON.toJSONString(student));
////                    kafkaTemplate.send("onecard.order.repayquotarecover", JSON.toJSONString(student));
//                    System.out.println("发送消息成功");
//                    try{
//                        Thread.sleep(1000);
//                    }
//                    catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread.start();
//    }
//}
