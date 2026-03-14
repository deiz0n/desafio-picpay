package org.deizon.authservice.domain.exceptions;

public class CnpjAlreadyExistsException extends CreationUserException {
    public CnpjAlreadyExistsException(String message) {
        super(message);
    }
}
