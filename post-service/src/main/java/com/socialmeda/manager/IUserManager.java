package com.socialmeda.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(url = "http://localhost:9090/auth",decode404 = true,name = "userprofile-auth")
public interface IUserManager {
    @GetMapping("/getuseridfromtoken/{token}")
    Long getUserIdFromToken(@PathVariable String token);
}
