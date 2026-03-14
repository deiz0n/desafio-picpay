package org.deizon.authservice.infrastructure.adapters.web;

import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.infrastructure.adapters.web.dtos.user.CreateUserDTO;
import org.deizon.authservice.infrastructure.adapters.web.dtos.user.UpdateUserDTO;
import org.deizon.authservice.infrastructure.adapters.web.dtos.user.UserResponseDTO;

public class UserWebMapper {

    public static UserModel toUserModel(CreateUserDTO dto) {
        return new UserModel(
                dto.id(),
                dto.fullName(),
                dto.cpf(),
                dto.cnpj(),
                dto.email(),
                dto.password(),
                dto.role()
        );
    }

    public static UserModel toUserModel(UpdateUserDTO dto) {
        return new UserModel(
                dto.id(),
                dto.fullName(),
                dto.cpf(),
                dto.cnpj(),
                dto.email(),
                dto.password(),
                dto.role()
        );
    }

    public static UserResponseDTO toUserResponseDTO(UserModel model) {
        return new UserResponseDTO(
                model.id(),
                model.fullName(),
                model.email(),
                model.role()
        );
    }


}
