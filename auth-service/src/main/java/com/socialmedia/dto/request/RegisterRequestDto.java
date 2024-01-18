package com.socialmedia.dto.request;

import com.socialmedia.constant.ErrorStaticMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {
    @NotBlank(message = "Kullanici adi bos gecilemez")
    @Size(min = 2,max = 20,message = ErrorStaticMessage.USERNAME_NOT_VALID)
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 2,max = 20)
    private String password;
    private String rePassword;
}
