package org.deizon.authservice.infrastructure.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.issuer.jwt}")
    private String issuer;
    @Value("${api.secret.key.jwt}")
    private String secret;

    public String generateToken(String subject) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer(issuer)
                    .withSubject(subject)
                    .withExpiresAt(expirationInstant())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("error creating token", e);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return   JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

    public Instant expirationInstant() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

}
