package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "activationQueue")
    public void newUserCreate(ActivationModel model){
        userProfileService.activateUser(model);
    }
}
