package com.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordDTO {

    private String email;
    private String password;
    private String newPassword;
}
