package org.deizon.authservice.application.services;

import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.application.useCases.ReadUsersUseCase;
import org.deizon.authservice.domain.UserModel;

import java.util.List;

public class ReadUsersService implements ReadUsersUseCase {

    private final UserRepositoryPort repository;

    public ReadUsersService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<UserModel> execute() {
        return repository.readUsers();
    }
}
