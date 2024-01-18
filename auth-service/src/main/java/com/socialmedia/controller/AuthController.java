package com.socialmedia.controller;

import com.socialmedia.dto.request.*;
import com.socialmedia.dto.response.DoLoginResponseDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.exception.AuthServiceException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.MailActivationModel;
import com.socialmedia.repository.entity.Auth;
import com.socialmedia.service.AuthService;
import com.socialmedia.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.socialmedia.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT+AUTH)
public class AuthController{

    private final AuthService authService;
    private final JwtTokenManager tokenManager;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new AuthServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(REGISTER+"rabbit")
    public ResponseEntity<RegisterResponseDto> registerWithRabbit(@RequestBody @Valid RegisterRequestDto dto){
        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new AuthServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);
        return ResponseEntity.ok(authService.registerWithRabbit(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<String> doLogin(DoLoginRequestDto dto){
        return ResponseEntity.ok(authService.doLogin(dto));
    }
    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<String> activateStatus(ActivationCodeRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }
    @PostMapping(ACTIVATE_STATUS+"rabbit")
    public ResponseEntity<String> activateStatusWithRabbit(ActivationModel model){
        return ResponseEntity.ok(authService.activateStatusWithRabbit(model));
    }

    @DeleteMapping(DELETE+"{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long authId){
        return ResponseEntity.ok(authService.softDelete(authId));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<String> update(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(authService.updateAuth(dto));
    }
    @GetMapping("/gettoken")
    public ResponseEntity<String> getToken(Long id){
        return ResponseEntity.ok(tokenManager.createToken(id).get());
    }
    @GetMapping("/idfromtoken")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(tokenManager.decodeTokenForId(token).get());
    }
    @GetMapping("/rolefromtoken")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(tokenManager.decodeTokenForRole(token).get());
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Auth>> findAll(){
        return ResponseEntity.ok(authService.findAll());
    }
}
