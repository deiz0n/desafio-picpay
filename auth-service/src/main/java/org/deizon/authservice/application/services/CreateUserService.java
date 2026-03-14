package org.deizon.authservice.application.services;

import org.deizon.authservice.application.ports.PasswordEncoderPort;
import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.application.useCases.CreateUserUseCase;
import org.deizon.authservice.domain.RoleEnum;
import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.domain.exceptions.CnpjAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.CpfAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.EmailAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.CreationUserException;

public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort repository;
    private final PasswordEncoderPort passwordEncoder;

    public CreateUserService(UserRepositoryPort repository, PasswordEncoderPort passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel execute(UserModel user) {
        isExists(user);
        isValid(user);

        UserModel userToSave = new UserModel(
                user.id(),
                user.fullName(),
                user.cpf(),
                user.cnpj(),
                user.email(),
                passwordEncoder.encode(user.password()),
                user.role()
        );

        return repository.upsert(userToSave);
    }

    private void isExists(UserModel user) {
        if (repository.findByEmail(user.email()).isPresent() && user.email() != null)
            throw new EmailAlreadyExistsException("Email already exists");
        if (repository.findByCnpj(user.cnpj()).isPresent() && user.cnpj() != null)
            throw new CnpjAlreadyExistsException("Cnpj already exists");
        if (repository.findByCpf(user.cpf()).isPresent() && user.cpf() != null)
            throw new CpfAlreadyExistsException("Cpf already exists");
    }

    private void isValid(UserModel user) {
        if (user.role() == RoleEnum.USER && user.cpf() == null)
            throw new CreationUserException("Cpf is null");
        if (user.role() == RoleEnum.MERCHANT && user.cnpj() == null)
            throw new CreationUserException("Cnpj is null");
    }
}
