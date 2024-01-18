package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.rabbitmq.model.MailActivationModel;
import com.socialmedia.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "activationMailQueue")
    public void newUserCreate(MailActivationModel model){
        mailSenderService.sendMail(model);
    }
}
