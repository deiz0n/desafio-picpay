package org.deizon.authservice.domain.exceptions;

public class CpfAlreadyExistsException extends UserAlreadyExistsException {
    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
