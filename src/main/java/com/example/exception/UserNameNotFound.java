package com.example.exception;

public class UserNameNotFound extends RuntimeException {
    public UserNameNotFound(String message) {
        super(message);
    }
}
