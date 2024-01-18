package com.socialmedia.repository.entity;

import com.socialmedia.repository.entity.enums.ERole;
import com.socialmedia.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.mapstruct.EnumMapping;

import javax.persistence.*;
import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tblauth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Length(min = 2,max = 20)
    private String username;
    @Column(unique = true,nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
    @Length(min = 2,max = 20)
    private String password;
    private String activationCode;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status=EStatus.PENDING;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ERole role=ERole.USER;
}
