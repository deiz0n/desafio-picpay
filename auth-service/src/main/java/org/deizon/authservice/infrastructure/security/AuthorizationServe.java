package org.deizon.authservice.infrastructure.security;

import org.deizon.authservice.infrastructure.adapters.persistence.UserEntity;
import org.deizon.authservice.infrastructure.adapters.persistence.repositories.UserRepositoryAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationServe implements UserDetailsService {

    private final UserRepositoryAdapter repositoryAdapter;

    public AuthorizationServe(UserRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public UserDetails loadUserByUsername(String username)   {
        Optional<UserEntity> user = Optional.ofNullable(repositoryAdapter.findByEmail(username));
        return user.orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("User with username: %s not found", username)
                )
        );
    }
}
