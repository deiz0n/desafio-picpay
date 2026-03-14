package org.deizon.authservice.domain.dtos;

public record ErrorDTO(
        int code,
        String title,
        String status,
        String description
) {}
