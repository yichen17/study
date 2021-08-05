package com.yichen.consumer;

import com.alibaba.fastjson.JSON;
import com.yichen.model.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/4 16:52
 * @describe
 */
@Component
public class Consumer {

    @KafkaListener(topics = "test")
    public void show(ConsumerRecord<String, String> record, Acknowledgment acknowledgment){
        try{
            String value= record.value();
            System.out.println(value);
            Student student= JSON.parseObject(value,Student.class);
            System.out.println("转换后的数据是"+student);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            acknowledgment.acknowledge();
        }
    }


}
