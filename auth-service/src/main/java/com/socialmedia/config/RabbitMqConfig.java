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

    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Value("${rabbitmq.activation-queue}")
    private String activationQueueName;

    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;

    @Value("${rabbitmq.activation-bindingKey}")
    private String activationBindingKey;

    @Value("${rabbitmq.activation-mail-queue}")
    private String mailQueueName;

    @Value("${rabbitmq.activation-bindingKey}")
    private String mailActivationBindingKey;

    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue registerQueue(){
        return new Queue(registerQueueName);
    }

    @Bean
    Queue activationQueue(){
        return new Queue(activationQueueName);
    }
    @Bean
    Queue mailQueue(){
        return new Queue("mailQueue");
    }

    @Bean
    public Binding bindingRegister(Queue registerQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }

    @Bean
    public Binding bindingActivation(Queue activationQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(activationQueue).to(exchangeAuth).with(activationBindingKey);
    }

    @Bean
    public Binding bindingMailActivation(Queue mailQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(mailQueue).to(exchangeAuth).with(mailActivationBindingKey);
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
