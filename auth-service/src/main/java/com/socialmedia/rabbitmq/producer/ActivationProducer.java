package com.socialmedia.rabbitmq.producer;

import com.socialmedia.rabbitmq.model.ActivationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String directExchange;
    @Value("${rabbitmq.activation-bindingKey}")
    private String registerBindingKey;
    public void convertAndSend(ActivationModel model) {
        rabbitTemplate.convertAndSend(directExchange,registerBindingKey,model);
    }
}
