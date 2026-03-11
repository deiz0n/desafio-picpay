package org.deizon.authservice.application.useCases;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID userId);
}
