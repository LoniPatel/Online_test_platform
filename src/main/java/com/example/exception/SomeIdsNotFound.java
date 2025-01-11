package com.example.exception;

public class SomeIdsNotFound extends RuntimeException {
    public SomeIdsNotFound(String message) {
        super(message);
    }
}
