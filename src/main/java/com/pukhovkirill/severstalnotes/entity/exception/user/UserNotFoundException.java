package com.pukhovkirill.severstalnotes.entity.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String name) {
        super(String.format("User '%s' not found", name));
    }

    public UserNotFoundException(String name, Throwable cause) {
        super(String.format("User '%s' not found", name), cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

}
