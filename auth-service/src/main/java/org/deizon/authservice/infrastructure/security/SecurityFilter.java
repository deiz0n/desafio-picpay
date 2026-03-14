package org.deizon.authservice.infrastructure.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deizon.authservice.infrastructure.adapters.persistence.UserEntity;
import org.deizon.authservice.infrastructure.adapters.persistence.repositories.UserRepositoryAdapter;
import org.deizon.authservice.infrastructure.security.services.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepositoryAdapter userRepository;

    public SecurityFilter(TokenService tokenService, UserRepositoryAdapter userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        String token = recoveryToken(request);

        if (token != null) {
            String subject = tokenService.validateToken(token);
            UserEntity user = userRepository.findByEmail(subject);

            if (user != null) {
                var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null)
            return authHeader.replace("Bearer ", "");
        return null;
    }
}
