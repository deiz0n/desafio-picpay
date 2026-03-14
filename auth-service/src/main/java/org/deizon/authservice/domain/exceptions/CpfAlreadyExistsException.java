package org.deizon.authservice.domain.exceptions;

public class CpfAlreadyExistsException extends CreationUserException {
    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
