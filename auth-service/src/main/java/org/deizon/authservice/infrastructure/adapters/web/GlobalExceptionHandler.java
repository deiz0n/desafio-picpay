package org.deizon.authservice.infrastructure.adapters.web;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.deizon.authservice.domain.dtos.ErrorDTO;
import org.deizon.authservice.domain.exceptions.*;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.naming.AuthenticationException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDTO(
                                HttpStatus.BAD_REQUEST.value(),
                                "Error creating user",
                                HttpStatus.BAD_REQUEST.name(),
                                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()
                        )
                );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorDTO(
                                HttpStatus.NOT_FOUND.value(),
                                "Resource not found",
                                HttpStatus.NOT_FOUND.name(),
                                String.format("Not found %s", request.getContextPath())
                        )
                );
    }

    @ExceptionHandler({
            InsufficientAuthenticationException.class,
            JWTVerificationException.class,
            TokenExpiredException.class
    })
    public ResponseEntity<ErrorDTO> handleInsufficientAuthenticationException() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorDTO(
                                HttpStatus.FORBIDDEN.value(),
                                "Resource not found",
                                HttpStatus.FORBIDDEN.name(),
                                "Token invalid, expired or null"
                        )
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDTO(
                                HttpStatus.BAD_REQUEST.value(),
                                "User not found",
                                HttpStatus.BAD_REQUEST.name(),
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(CreationUserException.class)
    public ResponseEntity<ErrorDTO> handleUserAlreadyExists(CreationUserException ex) {
        if (ex instanceof EmailAlreadyExistsException ||
            ex instanceof  CpfAlreadyExistsException ||
            ex instanceof CnpjAlreadyExistsException
        ) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            new ErrorDTO(
                                    HttpStatus.CONFLICT.value(),
                                    "Error creating user",
                                    HttpStatus.CONFLICT.name(),
                                    ex.getMessage()
                            )
                    );
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDTO(
                                HttpStatus.BAD_REQUEST.value(),
                                "Error creating user",
                                HttpStatus.BAD_REQUEST.name(),
                                ex.getMessage()
                        )
                );


    }

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorDTO> handleAuthenticationException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDTO(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Authentication failed",
                        HttpStatus.UNAUTHORIZED.name(),
                        "Email or password invalids"
                ));
    }
}



