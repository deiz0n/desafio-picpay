package org.deizon.authservice.domain.exceptions;

public class EmailAlreadyExistsException extends UserAlreadyExistsException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
