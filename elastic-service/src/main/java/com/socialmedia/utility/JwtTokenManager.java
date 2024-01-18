package com.socialmedia.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.socialmedia.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenManager {
   /*
   @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.issuer}")
    private String issuer;
    private final Long expTime = 1000L*60*5;
    // 1. Generate
    public Optional<String> createToken(Long id){
        String token = null;
        Date expDate = new Date(System.currentTimeMillis()+expTime);
        try {
            token = JWT.create()
                    .withClaim("id",id)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(expDate).sign(Algorithm.HMAC512(secretKey));
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
        return Optional.ofNullable(token);
    }

    // 2. Decode
    public Optional<Long> decodeTokenForId(String token){
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        }
        return Optional.of(decodedJWT.getClaim("id").asLong());
    }
    public Optional<String> decodeTokenForRole(String token){
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        if (decodedJWT==null)
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        return Optional.of(decodedJWT.getClaim("role").asString());
    }
    */
}
