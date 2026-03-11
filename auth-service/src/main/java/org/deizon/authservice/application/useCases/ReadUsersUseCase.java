package org.deizon.authservice.application.useCases;

import org.deizon.authservice.domain.UserModel;

import java.util.List;

public interface ReadUsersUseCase {
    List<UserModel> execute();
}
