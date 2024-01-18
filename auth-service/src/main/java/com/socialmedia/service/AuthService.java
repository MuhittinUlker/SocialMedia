package com.socialmedia.service;

import com.socialmedia.dto.request.ActivationCodeRequestDto;
import com.socialmedia.dto.request.DoLoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.exception.AuthServiceException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.MailActivationModel;
import com.socialmedia.rabbitmq.producer.ActivationProducer;
import com.socialmedia.rabbitmq.producer.MailSenderProducer;
import com.socialmedia.rabbitmq.producer.RegisterProducer;
import com.socialmedia.repository.IAuthRepository;
import com.socialmedia.repository.entity.Auth;
import com.socialmedia.repository.entity.enums.EStatus;
import com.socialmedia.utility.CodeGenerator;
import com.socialmedia.utility.JwtTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;
    private final JwtTokenManager tokenManager;
    private final IUserManager userManager;
    private final RegisterProducer registerProducer;
    private final MailSenderProducer mailSenderProducer;
    private final ActivationProducer activationProducer;
    public AuthService(IAuthRepository authRepository, JwtTokenManager tokenManager, IUserManager userManager, RegisterProducer registerProducer, MailSenderProducer mailSenderProducer, ActivationProducer activationProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.tokenManager = tokenManager;
        this.userManager = userManager;
        this.registerProducer = registerProducer;
        this.mailSenderProducer = mailSenderProducer;
        this.activationProducer = activationProducer;
    }
    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = null;
        try {
            if (authRepository.existsByEmail(dto.getEmail()))
                throw new AuthServiceException(ErrorType.REGISTER_EMAIL_ALREADY_EXISTS);
            auth = Auth.builder()
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .activationCode(CodeGenerator.generateCode())
                    .build();
            authRepository.save(auth);
            userManager.createNewUser(IAuthMapper.INSTANCE.toUserSaveRequestDto(auth));
        } catch (Exception e) {
            throw new AuthServiceException(ErrorType.INTERNAL_ERROR_SERVER);
        }
        return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
    }

    public RegisterResponseDto registerWithRabbit(RegisterRequestDto dto) {
        Auth auth = null;
        try {
            if (authRepository.existsByEmail(dto.getEmail()))
                throw new AuthServiceException(ErrorType.REGISTER_EMAIL_ALREADY_EXISTS);
            auth = Auth.builder()
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .activationCode(CodeGenerator.generateCode())
                    .build();
            authRepository.save(auth);
            registerProducer.convertAndSend(IAuthMapper.INSTANCE.toregistermodel(auth));
            mailSenderProducer.convertAndSend(IAuthMapper.INSTANCE.tomailactivationmodel(auth));
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        } catch (Exception e) {
            throw new AuthServiceException(ErrorType.INTERNAL_ERROR_SERVER);
        }
    }

    public String doLogin(DoLoginRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if (optionalAuth.isEmpty())
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        if (!optionalAuth.get().getStatus().equals(EStatus.ACTIVE))
            throw new AuthServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        Optional<String> token = tokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole());
        if (token.isEmpty())
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        return token.get();
    }
    @Transactional
    public String activateStatus(ActivationCodeRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findById(dto.getId());
        if (optionalAuth.isEmpty())
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        if (!optionalAuth.get().getActivationCode().equals(dto.getActivationCode()))
            throw new AuthServiceException(ErrorType.ACTIVATION_CODE_MISMATCH);
        return statusControl(optionalAuth.get());
    }
    private String statusControl(Auth auth){
        switch (auth.getStatus()){
            case ACTIVE -> {
                return "Hesap Zaten Aktif";
            }
            case PENDING -> {
                auth.setStatus(EStatus.ACTIVE);
                save(auth);
                activationProducer.convertAndSend(IAuthMapper.INSTANCE.toactivationmodel(auth));
                //userManager.updateStatus(auth.getId());
                return "Aktivasyon Basarili";
            }
            case BANNED -> {
                return "Hesabiniz Banlanmistir";
            }
            case DELETED -> {
                return "Hesabiniz silinmistir";
            }
            default -> {
                throw new AuthServiceException(ErrorType.INTERNAL_ERROR_SERVER);
            }
        }
    }

    public String softDelete(Long authId) {
        Optional<Auth> optionalAuth = authRepository.findById(authId);
        if (optionalAuth.isEmpty())
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        if (!optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            optionalAuth.get().setStatus(EStatus.DELETED);
            save(optionalAuth.get());
            return authId+ " id'li kullanici silindi";
        }else {
            throw new AuthServiceException(ErrorType.USER_ALREADY_DELETED);
        }
    }

    public String updateAuth(UpdateRequestDto dto) {
        Optional<Auth> optionalAuth = findById(dto.getId());
        Auth auth = optionalAuth.orElseThrow(() -> new AuthServiceException(ErrorType.USER_NOT_FOUND));
        if (authRepository.existsByEmail(dto.getEmail()))
            throw new AuthServiceException(ErrorType.REGISTER_EMAIL_ALREADY_EXISTS);
        auth.setEmail(dto.getEmail());
        update(auth);
        return "Guncelleme basarili";
    }

    public String activateStatusWithRabbit(ActivationModel model) {
        Optional<Auth> optionalAuth = authRepository.findById(model.getId());
        if (optionalAuth.isEmpty())
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        if (!optionalAuth.get().getActivationCode().equals(model.getActivationCode()))
            throw new AuthServiceException(ErrorType.ACTIVATION_CODE_MISMATCH);
        return statusControl(optionalAuth.get());
    }

}
