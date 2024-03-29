package com.socialmedia.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Value("${rabbitmq.user-exchange}")
    private String exchangeUser;

    @Value("${rabbitmq.elastic-queue}")
    private String elasticQueue;

    @Value("${rabbitmq.elastic-save-bindingKey}")
    private String elasticSaveBindingKey;
    @Bean
    DirectExchange exchangeUser(){
        return new DirectExchange(exchangeUser);
    }
    @Bean
    Queue elasticQueue() {
        return new Queue(elasticQueue);
    }

    @Bean
    public Binding bindingRegisterElastic(Queue elasticQueue, DirectExchange exchangeUser) {
        return BindingBuilder.bind(elasticQueue).to(exchangeUser).with(elasticSaveBindingKey);
    }

    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;

    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }
    @Bean
    Queue registerQueue(){
        return new Queue(registerQueueName);
    }

    @Bean
    public Binding bindingRegister(Queue registerQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }

    /*
    @Bean
    MessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }*/
}
