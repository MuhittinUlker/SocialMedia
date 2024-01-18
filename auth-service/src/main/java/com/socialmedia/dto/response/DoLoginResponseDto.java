package com.socialmedia.dto.response;

import com.socialmedia.repository.entity.enums.ERole;
import com.socialmedia.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoLoginResponseDto {
    private Long id;
    private String username;
    private String email;
    private ERole role;
    private EStatus status;
}
