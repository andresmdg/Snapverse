package org.codecollad.snapverse.exceptions.custom;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException (String message) {
        super(message);
    }
}
