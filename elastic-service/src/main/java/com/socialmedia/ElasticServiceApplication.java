package com.socialmedia;

import com.socialmedia.entity.UserProfile;
import com.socialmedia.service.UserProfileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ElasticServiceApplication {
    private final UserProfileService userProfileService;

    public ElasticServiceApplication(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ElasticServiceApplication.class);


    }

    private void saveAll(Iterable<UserProfile> userProfiles){
        userProfileService.saveAll(userProfiles);
    }
}