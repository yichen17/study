package com.yichen.rabbitmqcase.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/17 14:54
 */
@Component
public class Producer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void  create(){
        String message = "Hello RabbitMQ !";
        amqpTemplate.convertAndSend("hello", message);
        System.out.println("创建了一条消息 : " + message);
    }
}
