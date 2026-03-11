package org.deizon.authservice.domain;

import java.util.UUID;

public record UserModel(
        UUID id,
        String fullName,
        String cpf,
        String cnpj,
        String email,
        String password,
        RoleEnum role
) {}
