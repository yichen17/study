package com.yichen.rabbitmqcase.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/17 14:52
 */


@Configuration
public class RabbitMQConfiguration {

    private static final String QUEUE_SIMPLE_NAME = "hello";

    // 队列
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_SIMPLE_NAME);
    }

}

