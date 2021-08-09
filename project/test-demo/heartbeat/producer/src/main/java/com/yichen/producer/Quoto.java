package com.yichen.producer;

import com.alibaba.fastjson.JSON;
import com.yichen.model.OrgQuotaRecoverDTO;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/9 15:44
 * @describe OrgQuotaRecoverConsumer 测试
 */
@Component
public class Quoto implements InitializingBean, DisposableBean  {

    private Thread thread;
    private volatile boolean isStop=false;

    @Autowired(required=false)
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void destroy() throws Exception {
        isStop=true;
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        OrgQuotaRecoverDTO dto1=new OrgQuotaRecoverDTO();
        dto1.setAppId("1300002663");
//        dto1.setAppId("1300002645");
        dto1.setIsSettle("1");
//        dto1.setRepayAmt("10");
        dto1.setOrderRepayStatus("F3201");
        dto1.setRepayStatus("F3201");
        dto1.setTransNo("010561001112108090111059675008");
//        dto1.setTransNo("01881001112108090152102512003");
        dto1.setStage("1");
        dto1.setRepayType("F3102");

        OrgQuotaRecoverDTO dto2=new OrgQuotaRecoverDTO();
        dto2.setAppId("1300002663");
//        dto2.setAppId("1300002645");
        dto2.setIsSettle("1");
//        dto2.setRepayAmt("10");
        dto2.setOrderRepayStatus("F3201");
        dto2.setRepayStatus("F3201");
        dto2.setTransNo("010561001112108090111059675008");
//        dto2.setTransNo("01881001112108090152102512003");
        dto2.setStage("2");
        dto2.setRepayType("F3102");

        OrgQuotaRecoverDTO dto3=new OrgQuotaRecoverDTO();
        dto3.setAppId("1300002663");
//        dto3.setAppId("1300002645");
        dto3.setIsSettle("1");
//        dto3.setRepayAmt("10");
        dto3.setOrderRepayStatus("F3201");
        dto3.setRepayStatus("F3201");
        dto3.setTransNo("010561001112108090111059675008");
//        dto3.setTransNo("01881001112108090152102512003");
        dto3.setStage("3");
        dto3.setRepayType("F3102");

        List<OrgQuotaRecoverDTO >lists=new ArrayList<>();
        lists.add(dto1);
        lists.add(dto2);
        lists.add(dto3);
        System.out.println("...");
        kafkaTemplate.send("onecard.order.repayquotarecover", JSON.toJSONString(lists));
        System.out.println("发送成功");
//        thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(!isStop){
//                    try{
//                        System.out.println(JSON.toJSONString(lists));
//                        kafkaTemplate.send("onecard.order.repayquotarecover", JSON.toJSONString(lists));
//                        System.out.println("发送成功");
//                        Thread.sleep(1000);
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread.start();
    }
}
