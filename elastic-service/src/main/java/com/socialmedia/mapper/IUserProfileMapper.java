package com.socialmedia.mapper;

import com.socialmedia.dto.UserProfileResponseDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.rabbitmq.model.UserProfileSaveModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);


    List<UserProfile> toUserProfile(List<UserProfileResponseDto> list);

    UserProfile toUserProfile(UserProfileSaveModel model);
}
