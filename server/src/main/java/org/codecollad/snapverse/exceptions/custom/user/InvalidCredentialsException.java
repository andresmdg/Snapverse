package org.codecollad.snapverse.exceptions.custom.user;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException (String message) {
        super(message);
    }
}
