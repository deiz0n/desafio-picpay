package org.deizon.authservice.application.useCases;

import org.deizon.authservice.domain.UserModel;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserModel execute(UUID userId);
}
