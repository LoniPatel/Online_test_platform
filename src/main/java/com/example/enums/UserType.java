package com.example.enums;

import lombok.Getter;

@Getter
public enum UserType {

    ADMIN("Admin"),
    EMPLOYER("Employer"),
    CANDIDATE("Candidate");

    private String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}