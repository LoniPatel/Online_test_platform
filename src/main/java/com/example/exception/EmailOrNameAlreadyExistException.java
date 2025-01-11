package com.example.exception;

public class EmailOrNameAlreadyExistException extends RuntimeException {
    public EmailOrNameAlreadyExistException(String message) {
        super(message);
    }
}
