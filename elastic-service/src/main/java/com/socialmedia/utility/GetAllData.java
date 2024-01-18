package com.socialmedia.utility;

import com.socialmedia.dto.UserProfileResponseDto;
import com.socialmedia.manager.IUserProfileManager;
import com.socialmedia.mapper.IUserProfileMapper;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetAllData {
    private final IUserProfileManager iUserProfileManager;
    private final UserProfileService userProfileService;

    //@PostConstruct
    public void initData(){
        List<UserProfileResponseDto> list = iUserProfileManager.findAllForElasticService().getBody();
        userProfileService.saveAll(IUserProfileMapper.INSTANCE.toUserProfile(list));
    }
}
