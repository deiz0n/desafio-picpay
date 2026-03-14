package org.deizon.authservice.infrastructure.adapters.web.controllers;

import jakarta.validation.Valid;
import org.deizon.authservice.application.useCases.*;
import org.deizon.authservice.domain.UserModel;
import org.deizon.authservice.infrastructure.adapters.web.UserWebMapper;
import org.deizon.authservice.infrastructure.adapters.web.dtos.user.CreateUserDTO;
import org.deizon.authservice.infrastructure.adapters.web.dtos.user.UpdateUserDTO;
import org.deizon.authservice.infrastructure.adapters.web.dtos.user.UserResponseDTO;
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
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid CreateUserDTO user) {
        UserModel response = createUserUseCase.execute(UserWebMapper.toUserModel(user));
        return ResponseEntity.ok(
                UserWebMapper.toUserResponseDTO(response)
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable UUID userId) {
        deleteUserUseCase.execute(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> readUsers() {
        List<UserModel> response = readUsersUseCase.execute();
        return ResponseEntity.ok(
                response.stream()
                        .map(UserWebMapper::toUserResponseDTO)
                        .toList()
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> readUser(@PathVariable UUID userId) {
        UserModel response = readUserUseCase.execute(userId);
        return ResponseEntity.ok(
                UserWebMapper.toUserResponseDTO(response)
        );
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID userId, @RequestBody @Valid UpdateUserDTO user) {
        UserModel response = updateUserUseCase.execute(userId, UserWebMapper.toUserModel(user));
        return ResponseEntity.ok(
                UserWebMapper.toUserResponseDTO(response)
        );
    }
}
