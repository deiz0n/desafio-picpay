package org.deizon.authservice.infrastructure.adapters.web.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.deizon.authservice.domain.RoleEnum;

import java.util.UUID;


public record UserResponseDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        String fullName,
        String email,
        RoleEnum role
) {}
