package org.deizon.authservice.domain.exceptions;

public class CnpjAlreadyExistsException extends UserAlreadyExistsException {
    public CnpjAlreadyExistsException(String message) {
        super(message);
    }
}
