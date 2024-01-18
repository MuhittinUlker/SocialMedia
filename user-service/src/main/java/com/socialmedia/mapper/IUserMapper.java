package com.socialmedia.mapper;


import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.rabbitmq.model.UserProfileSaveModel;
import com.socialmedia.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    UserProfile toUserProfile(UserSaveRequestDto dto);
    @Mapping(target = "id",source = "authId")
    UpdateRequestDto toUpdateReqDto(UserProfile userProfile);

    UserProfile toUserProfile(RegisterModel registerModel);
    @Mapping(target = "authId",source = "id")
    UserProfile toUserProfile(ActivationModel model);

    UserProfileResponseDto toUserProfileResponseDto(UserProfile userProfile);

    UserProfileSaveModel toUserModel(UserProfile userProfile);
}
