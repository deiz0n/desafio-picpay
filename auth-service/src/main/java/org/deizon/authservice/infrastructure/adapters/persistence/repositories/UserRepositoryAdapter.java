package org.deizon.authservice.infrastructure.adapters.persistence.repositories;

import org.deizon.authservice.infrastructure.adapters.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryAdapter extends JpaRepository<UserEntity, UUID> {

    UserEntity findByCpf(String cpf);
    UserEntity findByCnpj(String cnpj);
    UserEntity findByEmail(String email);

}
