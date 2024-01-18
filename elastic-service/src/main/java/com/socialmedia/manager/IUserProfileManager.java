package com.socialmedia.manager;

import com.socialmedia.dto.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(url = "http://localhost:9091/api/v1/user",decode404 = true,name = "elastic-userprofile")
public interface IUserProfileManager {
    @GetMapping("/findall/forelastic")
    public ResponseEntity<List<UserProfileResponseDto>> findAllForElasticService();
}
