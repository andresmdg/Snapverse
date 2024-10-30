package org.codecollad.snapverse.exceptions.custom.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (String message) {
        super(message);
    }

}
