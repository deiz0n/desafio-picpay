package org.deizon.authservice.infrastructure.utils;

import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.infrastructure.ports.persistence.UserEntity;

public class UserMapper {

     public static UserModel toModel(UserEntity entity) {
        return new UserModel(
                entity.getId(),
                entity.getFullName(),
                entity.getCpf(),
                entity.getCnpj(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole()
        );
    }

    public static UserEntity toEntity(UserModel model) {
        return new UserEntity(
                model.id(),
                model.fullName(),
                model.cpf(),
                model.cnpj(),
                model.email(),
                model.password(),
                model.role()
        );
    }
}
