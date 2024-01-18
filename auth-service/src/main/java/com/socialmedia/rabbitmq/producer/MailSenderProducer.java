package com.socialmedia.rabbitmq.producer;

import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.MailActivationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String directExchange;
    @Value("${rabbitmq.activation-mail-bindingKey}")
    private String activationMailBindingKey;
    public void convertAndSend(MailActivationModel model) {
        rabbitTemplate.convertAndSend(directExchange,activationMailBindingKey,model);
    }
}
