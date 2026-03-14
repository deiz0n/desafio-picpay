package org.deizon.authservice.infrastructure.security.services;

import org.deizon.authservice.infrastructure.adapters.web.dtos.LoginDTO;
import org.deizon.authservice.infrastructure.adapters.web.dtos.TokenDTO;
import org.deizon.authservice.infrastructure.adapters.persistence.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthService(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    public TokenDTO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        Authentication userAuthenticate = manager.authenticate(user);
        UserEntity userEntity = (UserEntity) userAuthenticate.getPrincipal();
        String token = tokenService.generateToken(userEntity.getEmail());

        return new TokenDTO(token);
    }

}
