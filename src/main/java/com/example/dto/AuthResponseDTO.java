package com.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponseDTO {
    private String status;
    private String message;
    private AuthDataDTO data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class AuthDataDTO {
        private String token;
        private String name;
        private String emailId;
        private String role;
    }
}