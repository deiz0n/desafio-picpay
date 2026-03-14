package org.deizon.authservice.infrastructure.adapters.web.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.deizon.authservice.domain.RoleEnum;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record CreateUserDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        @NotBlank(message = "fullName is required")
        String fullName,
        @CPF(message = "CPF invalid")
        String cpf,
        @CNPJ(message = "CNPJ invalid")
        String cnpj,
        @Email(message = "Email invalid")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Role is required")
        RoleEnum role
) {
}
