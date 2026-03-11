package org.deizon.authservice.application.useCases;

import org.deizon.authservice.domain.UserModel;

import java.util.UUID;

public interface ReadUserUseCase {
    UserModel execute(UUID userId);
}
