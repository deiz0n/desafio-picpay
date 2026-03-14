package org.deizon.authservice.infrastructure.adapters.web.dtos.user;

import jakarta.validation.constraints.Email;
import org.deizon.authservice.domain.RoleEnum;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record UpdateUserDTO(
        UUID id,
        String fullName,
        @CPF(message = "CPF invalid")
        String cpf,
        @CNPJ(message = "CNPJ invalid")
        String cnpj,
        @Email(message = "Email invalid")
        String email,
        String password,
        RoleEnum role
) {
}
