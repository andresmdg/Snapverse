package org.codecollad.snapverse.exceptions.custom.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException (String message) {
        super(message);
    }
}
