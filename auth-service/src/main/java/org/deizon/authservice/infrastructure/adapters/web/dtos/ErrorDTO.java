package org.deizon.authservice.infrastructure.adapters.web.dtos;

public record ErrorDTO(
        int code,
        String title,
        String status,
        String description
) {}
