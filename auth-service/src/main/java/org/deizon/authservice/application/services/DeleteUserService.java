package org.deizon.authservice.application.services;

import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.application.useCases.DeleteUserUseCase;
import org.deizon.authservice.domain.exceptions.UserNotFoundException;

import java.util.UUID;

public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepositoryPort repository;

    public DeleteUserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID userId) {
        if (repository.readUser(userId).isEmpty())
            throw new UserNotFoundException("User with id %s not found");

        repository.delete(userId);
    }
}
