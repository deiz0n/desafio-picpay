package org.deizon.authservice.infrastructure.adapters.web.controllers;

import jakarta.validation.Valid;
import org.deizon.authservice.infrastructure.adapters.web.dtos.LoginDTO;
import org.deizon.authservice.infrastructure.adapters.web.dtos.TokenDTO;
import org.deizon.authservice.infrastructure.security.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        TokenDTO response =  authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

}
