package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.mapper.IUserProfileMapper;
import com.socialmedia.rabbitmq.model.UserProfileSaveModel;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSaveConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "elasticQueue")
    public void newUserCreate(UserProfileSaveModel model){
        userProfileService.save(IUserProfileMapper.INSTANCE.toUserProfile(model));
    }
}
