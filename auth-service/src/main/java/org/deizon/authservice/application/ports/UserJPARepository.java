package org.deizon.authservice.application.ports;

import org.deizon.authservice.domain.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserJPARepository {
    UserModel create(UserModel user);
    void delete(UUID userId);
    List<UserModel> readUsers();
    UserModel readUser(UUID userId);
    UserModel update(UUID userId, UserModel user);
}
