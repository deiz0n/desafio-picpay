package org.deizon.authservice.application.useCases;

import org.deizon.authservice.domain.UserModel;

public interface CreateUserUseCase {
    UserModel execute(UserModel user);
}
