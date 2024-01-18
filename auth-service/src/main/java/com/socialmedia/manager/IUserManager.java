package com.socialmedia.manager;

import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.request.UpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.socialmedia.constant.EndPoints.*;

@FeignClient(url = "http://localhost:9091/api/v1/user",name = "auth-userprofile",decode404 = true)
public interface IUserManager {
    @PostMapping(SAVE)
    ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto);
    @PostMapping(UPDATE_STATUS)
    public ResponseEntity<Boolean> updateStatus(Long id);
    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> updateUser(@RequestBody UpdateRequestDto dto);
}
