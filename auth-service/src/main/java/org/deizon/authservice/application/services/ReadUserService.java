package org.deizon.authservice.application.services;

import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.application.useCases.ReadUserUseCase;
import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.domain.exceptions.UserNotFoundException;

import java.util.UUID;

public class ReadUserService implements ReadUserUseCase {

    private final UserRepositoryPort repository;

    public ReadUserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public UserModel execute(UUID userId) {
        return repository.readUser(userId)
                .orElseThrow(
                        () ->  new UserNotFoundException(String.format("User with id %s not found", userId.toString()))
                );
    }
}
