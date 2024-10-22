package org.codecollad.snapverse.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (String message) {
        super(message);
    }

}
