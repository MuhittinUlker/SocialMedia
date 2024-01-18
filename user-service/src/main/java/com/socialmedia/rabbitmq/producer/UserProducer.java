package com.socialmedia.rabbitmq.producer;

import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.UserProfileSaveModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.elastic-exchange}")
    private String directExchange;
    @Value("${rabbitmq.elastic-save-bindingKey}")
    private String elasticSaveBindingKey;
    public void convertAndSend(UserProfileSaveModel model) {
        rabbitTemplate.convertAndSend(directExchange,elasticSaveBindingKey,model);
    }
}
