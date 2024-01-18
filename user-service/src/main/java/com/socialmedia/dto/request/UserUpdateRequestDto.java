package com.socialmedia.dto.request;

import com.socialmedia.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdateRequestDto {
    @NotBlank
    private String token;
    @Email
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String about;
}
