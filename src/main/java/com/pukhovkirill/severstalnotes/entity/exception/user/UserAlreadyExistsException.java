package com.pukhovkirill.severstalnotes.entity.exception.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String name) {
        super(String.format("User '%s' already exists", name));
    }

    public UserAlreadyExistsException(String name, Throwable cause) {
        super(String.format("User '%s' already exists", name), cause);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
