package com.socialmedia.manager;

import com.socialmedia.dto.request.UpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.socialmedia.constant.EndPoints.UPDATE;

@FeignClient(url = "http://localhost:9090/auth",decode404 = true,name = "userprofile-auth")
public interface IAuthManager {
    @PutMapping(UPDATE)
    ResponseEntity<String> update(@RequestBody UpdateRequestDto dto);
}
