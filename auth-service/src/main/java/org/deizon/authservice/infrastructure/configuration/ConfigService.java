package org.deizon.authservice.infrastructure.configuration;

import org.deizon.authservice.application.ports.PasswordEncoderPort;
import org.deizon.authservice.application.ports.UserRepositoryPort;
import org.deizon.authservice.application.services.*;
import org.deizon.authservice.application.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigService {

    @Bean
    public CreateUserUseCase getCreateUserService(
            UserRepositoryPort repository,
            PasswordEncoderPort passwordEncoder
    ) {
        return new CreateUserService(repository, passwordEncoder);
    }

    @Bean
    public DeleteUserUseCase getDeleteUserService(UserRepositoryPort repository) {
        return new DeleteUserService(repository);
    }

    @Bean
    public ReadUsersUseCase getReadUsersService(UserRepositoryPort repository) {
        return new ReadUsersService(repository);
    }

    @Bean
    public ReadUserUseCase getReadUserService(UserRepositoryPort repository) {
        return new ReadUserService(repository);
    }

    @Bean
    public UpdateUserUseCase getUpdateUserService(
            UserRepositoryPort repository,
            PasswordEncoderPort passwordEncoder
    ) {
        return new UpdateUserService(repository, passwordEncoder);
    }
}
