package com.yichen.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/4 16:48
 * @describe
 */
@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, String> kafkaBusinessTemplate() {
        KafkaTemplate<String, String> kafkaBusinessTemplate = new KafkaTemplate<String, String>(producerFactory());
        return kafkaBusinessTemplate;
    }

    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1048576);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<String, String>(properties);
    }


}

