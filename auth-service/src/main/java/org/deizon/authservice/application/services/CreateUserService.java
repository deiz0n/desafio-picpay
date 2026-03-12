package org.deizon.authservice.application.services;

import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.application.useCases.CreateUserUseCase;
import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.domain.exceptions.CnpjAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.CpfAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.EmailAlreadyExistsException;

public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort repository;

    public CreateUserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public UserModel execute(UserModel user) {
        isExists(user);
        return repository.upsert(user);
    }

    private void isExists(UserModel user) {
        if (repository.findByEmail(user.email()).isPresent())
            throw new EmailAlreadyExistsException("Email already exists");
        if (repository.findByCnpj(user.cnpj()).isPresent())
            throw new CnpjAlreadyExistsException("Cnpj already exists");
        if (repository.findByCpf(user.cpf()).isPresent())
            throw new CpfAlreadyExistsException("Cpf already exists");
    }
}
