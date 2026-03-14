package org.deizon.authservice.infrastructure.adapters.web;

import jakarta.validation.Valid;
import org.deizon.authservice.application.useCases.*;
import org.deizon.authservice.domain.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ReadUsersUseCase readUsersUseCase;
    private final ReadUserUseCase readUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserRestController(
            CreateUserUseCase createUserUseCase,
            DeleteUserUseCase deleteUserUseCase,
            ReadUsersUseCase readUsersUseCase,
            ReadUserUseCase readUserUseCase,
            UpdateUserUseCase updateUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.readUsersUseCase = readUsersUseCase;
        this.readUserUseCase = readUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody @Valid UserModel user) {
        UserModel response = createUserUseCase.execute(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable UUID userId) {
        deleteUserUseCase.execute(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> readUsers() {
        List<UserModel> response = readUsersUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> readUser(@PathVariable UUID userId) {
        UserModel response = readUserUseCase.execute(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> update(@PathVariable UUID userId, @RequestBody @Valid UserModel user) {
        UserModel response = updateUserUseCase.execute(userId, user);
        return ResponseEntity.ok(response);
    }
}
