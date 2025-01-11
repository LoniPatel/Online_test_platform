package com.example.service;

import com.example.dto.*;

public interface UserService {

    ResponseDTO registerUser(RegisterDTO registerDTO);

    ResponseDTO verifyUser(String email, String password);

    ResponseDTO loginUser(LoginDTO loginDTO);

    ResponseDTO forgetPassword(String email);

    ResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO);


}
