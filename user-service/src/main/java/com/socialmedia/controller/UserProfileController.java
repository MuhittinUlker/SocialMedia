package com.socialmedia.controller;

import static com.socialmedia.constant.EndPoints.*;

import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.request.UserUpdateRequestDto;
import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ROOT+USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userService;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto){
        return ResponseEntity.ok(userService.createNewUser(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserUpdateRequestDto dto){
        return ResponseEntity.ok(userService.updateWithDto(dto));
    }
    @PostMapping(UPDATE_STATUS+"/{authId}")
    public ResponseEntity<String> updateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userService.updateStatus(authId));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

//    @GetMapping("/getuseridfromtoken/{token}")
//    public Long getUserIdFromToken(@PathVariable String token){
//        return userService.getUserIdFromToken(token);
//    }

    @GetMapping("/finduserbyusername/{username}")
    public ResponseEntity<UserProfile> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/findall/forelastic")
    public ResponseEntity<List<UserProfileResponseDto>> findAllForElasticService(){
        return ResponseEntity.ok(userService.findAllForElasticService());
    }

    @GetMapping("/findallbypageable")
    public ResponseEntity<Page<UserProfile>> findAllByPageable
            (int size,int pageNumber,@RequestParam(required = false,defaultValue = "ASC") String direction,@RequestParam(required = false,defaultValue = "id")String sortParameter){
        return ResponseEntity.ok(userService.findAllByPageable(size,pageNumber,direction,sortParameter));
    }

    @GetMapping("/findallbyslice")
    public ResponseEntity<Slice<UserProfile>> findAllBySlice
            (int size,int pageNumber,@RequestParam(required = false,defaultValue = "ASC") String direction,@RequestParam(required = false,defaultValue = "id")String sortParameter){
        return ResponseEntity.ok(userService.findAllBySlice(size,pageNumber,direction,sortParameter));
    }


}
