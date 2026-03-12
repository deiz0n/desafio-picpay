package org.deizon.authservice.infrastructure.adapters.persistence.repositories;

import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.infrastructure.adapters.persistence.UserEntity;
import org.deizon.authservice.infrastructure.utils.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryImpl implements UserRepositoryPort {

    private final UserRepositoryAdapter repository;

    public UserRepositoryImpl(UserRepositoryAdapter repository) {
        this.repository = repository;
    }

    @Override
    public UserModel upsert(UserModel user) {
        UserEntity entity = repository.save(UserMapper.toEntity(user));
        return UserMapper.toModel(entity);
    }

    @Override
    public void delete(UUID userId) {
        repository.deleteById(userId);
    }

    @Override
    public List<UserModel> readUsers() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toModel)
                .toList();
    }

    @Override
    public Optional<UserModel> readUser(UUID userId) {
        return repository.findById(userId).map(UserMapper::toModel);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email))
                .map(UserMapper::toModel);
    }

    @Override
    public Optional<UserModel> findByCpf(String cpf) {
        return Optional.ofNullable(repository.findByCpf(cpf))
                .map(UserMapper::toModel);
    }

    @Override
    public Optional<UserModel> findByCnpj(String cnpj) {
        return Optional.ofNullable(repository.findByCnpj(cnpj))
                .map(UserMapper::toModel);
    }
}
