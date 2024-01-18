package com.socialmedia.mapper;


import com.socialmedia.dto.request.DoLoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.DoLoginResponseDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.rabbitmq.model.ActivationModel;
import com.socialmedia.rabbitmq.model.MailActivationModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth registerRequestDtoToAuth(RegisterRequestDto dto);

    Auth doLoginDtoToAuth(DoLoginRequestDto dto);
    DoLoginResponseDto authToDoLoginResponseDto(Auth auth);
    @Mapping(source = "id",target = "authId")
    UserSaveRequestDto toUserSaveRequestDto(Auth auth);
    RegisterResponseDto toRegisterResponseDto(Auth auth);
    @Mapping(source = "id",target = "authId")
    RegisterModel toregistermodel(Auth auth);
    ActivationModel toactivationmodel(Auth auth);

    MailActivationModel tomailactivationmodel(Auth auth);
}
