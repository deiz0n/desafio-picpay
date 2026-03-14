package org.deizon.authservice.application.services;

import org.deizon.authservice.application.ports.PasswordEncoderPort;
import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.application.useCases.UpdateUserUseCase;
import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.domain.exceptions.CnpjAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.CpfAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.EmailAlreadyExistsException;
import org.deizon.authservice.domain.exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort repository;
    private final PasswordEncoderPort passwordEncoder;

    public UpdateUserService(UserRepositoryPort repository, PasswordEncoderPort passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel execute(UUID userId, UserModel user) {
        UserModel userModel = repository.readUser(userId).orElseThrow(
                () -> new UserNotFoundException("User with id %s not found")
        );

        isExists(userId, user);

        UserModel updatedUser = new UserModel(
                userModel.id(),
                updateIfNotNull(user.fullName(), userModel.fullName()),
                updateIfNotNull(user.cpf(), userModel.cpf()),
                updateIfNotNull(user.cnpj(), userModel.cnpj()),
                updateIfNotNull(user.email(), userModel.email()),

                user.password() != null
                    ? passwordEncoder.encode(user.password())
                    : userModel.password(),

                user.role() != null ? user.role() : userModel.role()
        );

        return repository.upsert(updatedUser);
    }

    private void isExists(UUID userId, UserModel user) {
        if (user.cnpj() != null) {
            Optional<UserModel> userByCnpj = repository.findByCnpj(user.cnpj());
            if (userByCnpj.isPresent() && !userId.equals(userByCnpj.get().id()))
                throw new CnpjAlreadyExistsException("Cnpj already exists");
        }

        if (user.cpf() != null) {
            Optional<UserModel> userByCpf = repository.findByCpf(user.cpf());
            if (userByCpf.isPresent() && !userId.equals(userByCpf.get().id()))
                throw new CpfAlreadyExistsException("Cpf already exists");

        }

        if (user.email() != null) {
            Optional<UserModel> userByEmail = repository.findByEmail(user.email());
            if (userByEmail.isPresent() && !userId.equals(userByEmail.get().id()))
                throw new EmailAlreadyExistsException("Email already exists");
        }
    }

    private String updateIfNotNull(String newValue, String currentValue) {
        return newValue != null && !newValue.equals(currentValue) ? newValue : currentValue;
    }
}
