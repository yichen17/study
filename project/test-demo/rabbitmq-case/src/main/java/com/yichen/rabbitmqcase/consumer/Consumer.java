package com.yichen.rabbitmqcase.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/17 14:53
 */
@Component
@RabbitListener(queues = "hello")
public class Consumer {

    @RabbitHandler
    public void use(String message) {
        System.out.println("消费了一条消息 : " + message);
    }

}
