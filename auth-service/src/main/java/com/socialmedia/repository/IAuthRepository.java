package com.socialmedia.repository;

import com.socialmedia.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthRepository extends JpaRepository<Auth,Long> {
    boolean existsByEmail(String email);

    Optional<Auth> findOptionalByEmailAndPassword(String email,String password);
}
