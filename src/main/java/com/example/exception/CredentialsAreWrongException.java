package com.example.exception;

public class CredentialsAreWrongException extends RuntimeException {
    public CredentialsAreWrongException(String message) {
        super(message);
    }
}
