package com.example.controller;

import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.dto.ResetPasswordDTO;
import com.example.dto.ResponseDTO;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @PostMapping("/registerUser")
    public ResponseDTO registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        logger.info("UserController : RegisterUser");
        return userService.registerUser(registerDTO);
    }

    @GetMapping("/verifyUser")
    public ResponseDTO verifyUser(@RequestParam String email, @RequestParam String password) {
        logger.info("UserController : verifyUser");
        return userService.verifyUser(email, password);
    }

    @PostMapping("/loginUser")
    public ResponseDTO loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        logger.info("UserController : LoginUser");
        return userService.loginUser(loginDTO);
    }

    @PostMapping("/forgetPassword")
    public ResponseDTO forgetPassword(@RequestParam String email) {
        logger.info("UserController : ForgetPassword");
        return userService.forgetPassword(email);
    }

    @PostMapping("/resetPassword")
    public ResponseDTO resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        logger.info("UserController : ResetPassword");
        return userService.resetPassword(resetPasswordDTO);
    }
}