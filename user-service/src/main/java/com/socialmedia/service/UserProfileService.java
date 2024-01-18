package com.socialmedia.service;

import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.request.UserUpdateRequestDto;
import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserServiceException;
import com.socialmedia.manager.IAuthManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.rabbitmq.producer.UserProducer;
import com.socialmedia.repository.IUserProfileRepository;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.repository.entity.enums.EStatus;
import com.socialmedia.utility.JwtTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.apache.catalina.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.socialmedia.exception.ErrorType.*;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository userRepository;
    private final JwtTokenManager tokenManager;
    private final IAuthManager authManager;
    private final UserProducer userProducer;

    public UserProfileService(IUserProfileRepository userRepository, JwtTokenManager tokenManager, IAuthManager authManager, UserProducer userProducer) {
        super(userRepository);
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
        this.authManager = authManager;
        this.userProducer = userProducer;
    }

    public Boolean createNewUser(UserSaveRequestDto dto) {
        UserProfile userProfile = IUserMapper.INSTANCE.toUserProfile(dto);
        save(userProfile);
        return true;
    }

    public String updateWithDto(UserUpdateRequestDto dto) {
        Optional<Long> authId = tokenManager.decodeTokenForId(dto.getToken());
        if (authId.isEmpty())
            throw new UserServiceException(INVALID_TOKEN);
        Optional<UserProfile> optionalUserProfile = userRepository.findByAuthId(authId.get());
        if (optionalUserProfile.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        if(!optionalUserProfile.get().getStatus().equals(EStatus.ACTIVE))
            throw new UserServiceException(USER_NOT_ACTIVE);
        if (!optionalUserProfile.get().getEmail().equals(dto.getEmail())) {
            optionalUserProfile.get().setEmail(dto.getEmail() == null ? optionalUserProfile.get().getEmail() : dto.getEmail());
            authManager.update(IUserMapper.INSTANCE.toUpdateReqDto(optionalUserProfile.get()));
        }
        optionalUserProfile.get().setAvatar(dto.getAvatar()==null?optionalUserProfile.get().getAvatar():dto.getAvatar());
        optionalUserProfile.get().setAddress(dto.getAddress()==null?optionalUserProfile.get().getAddress():dto.getAddress());
        optionalUserProfile.get().setEmail(dto.getAbout()==null?optionalUserProfile.get().getAbout():dto.getAbout());
        optionalUserProfile.get().setEmail(dto.getPhone()==null?optionalUserProfile.get().getPhone():dto.getPhone());
        update(optionalUserProfile.get());
        return "Guncelleme basarili";
    }

    public String updateStatus(Long id) {
        Optional<UserProfile> optionalUserProfile = userRepository.findByAuthId(id);
        if (optionalUserProfile.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        if (optionalUserProfile.get().getStatus().equals(EStatus.ACTIVE))
            throw new UserServiceException(USER_ALREADY_ACTIVE);
        optionalUserProfile.get().setStatus(EStatus.ACTIVE);
        save(optionalUserProfile.get());
        return "Hesap Basari ile aktive edildi";
    }


//    public Long getUserIdFromToken(String token) {
//        Optional<Long> authId = tokenManager.decodeTokenForId(token);
//        if (authId.isEmpty())
//            throw new UserServiceException(INVALID_TOKEN);
//        Optional<UserProfile> optionalUserProfile = userRepository.findByAuthId(authId.get());
//        UserProfile userProfile = optionalUserProfile.orElseThrow(()-> new UserServiceException(USER_NOT_FOUND));
//        return userProfile.getId();
//    }

    public void createNewUserWithRabbit(RegisterModel registerModel) {
        UserProfile userProfile = IUserMapper.INSTANCE.toUserProfile(registerModel);
        save(userProfile);
        userProducer.convertAndSend(IUserMapper.INSTANCE.toUserModel(userProfile));
    }

    public void activateUser(ActivationModel model) {
        Optional<UserProfile> optionalUserProfile = userRepository.findByAuthId(IUserMapper.INSTANCE.toUserProfile(model).getAuthId());
        if (optionalUserProfile.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        if (optionalUserProfile.get().getStatus().equals(EStatus.ACTIVE))
            throw new UserServiceException(USER_ALREADY_ACTIVE);
        optionalUserProfile.get().setStatus(EStatus.ACTIVE);
        save(optionalUserProfile.get());
    }
    
    @Cacheable(value = "userprofilebyusername")
    public UserProfile findByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()-> new UserServiceException(USER_NOT_FOUND));
    }

    public List<UserProfileResponseDto> findAllForElasticService() {
        return userRepository.findAll()
                .stream()
                .map(x -> IUserMapper.INSTANCE.toUserProfileResponseDto(x))
                .collect(Collectors.toList());
    }

    public Page<UserProfile> findAllByPageable(int size, int pageNumber, String direction, String sortParameter) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortParameter);
        Pageable pageable = PageRequest.of(pageNumber,size,sort);
        return userRepository.findAll(pageable);
    }

    public Slice<UserProfile> findAllBySlice(int size, int pageNumber, String direction, String sortParameter) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortParameter);
        Pageable pageable = PageRequest.of(pageNumber,size,sort);
        return userRepository.findAll(pageable);
    }
}
