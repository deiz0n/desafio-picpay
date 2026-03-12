package org.deizon.authservice.application.ports;

import org.deizon.authservice.domain.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    UserModel upsert(UserModel user);
    void delete(UUID userId);
    List<UserModel> readUsers();
    Optional<UserModel> readUser(UUID userId);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByCpf(String cpf);
    Optional<UserModel> findByCnpj(String cnpj);
}
