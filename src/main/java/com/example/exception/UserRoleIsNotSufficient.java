package com.example.exception;

public class UserRoleIsNotSufficient extends RuntimeException {
    public UserRoleIsNotSufficient(String message) {
        super(message);
    }
}
