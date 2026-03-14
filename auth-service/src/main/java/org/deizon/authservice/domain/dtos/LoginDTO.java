package org.deizon.authservice.domain.dtos;

public record LoginDTO(
        String email,
        String password
) {}
