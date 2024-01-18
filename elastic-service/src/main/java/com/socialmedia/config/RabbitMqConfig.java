package com.socialmedia.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.elastic-queue}")
    private String elasticQueue;

    @Bean
    Queue elasticQueue() {
        return new Queue(elasticQueue);
    }

}