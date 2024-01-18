package com.socialmedia.rabbitmq.model;

import com.socialmedia.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileSaveModel implements Serializable {
    private String id;
    private Long authId;
    private String username;
    private String email;
}
