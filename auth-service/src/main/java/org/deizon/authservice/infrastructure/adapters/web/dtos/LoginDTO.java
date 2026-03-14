package org.deizon.authservice.infrastructure.adapters.web.dtos;

public record LoginDTO(
        String email,
        String password
) {}
