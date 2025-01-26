package com.example.serviceImpl;

import com.example.dto.*;
import com.example.entity.User;
import com.example.enums.UserType;
import com.example.exception.*;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import com.example.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    EmailService emailService;

    @Override
    public ResponseDTO registerUser(RegisterDTO registerDTO) {
        logger.info("UserServiceImpl : RegisterUser");

        User existingData = userRepository.findByNameEmail(registerDTO.getName(), registerDTO.getEmail());
        if (existingData == null) {
            User user = new User();
            try {
                logger.info("Set User Details");
                user.setName(registerDTO.getName());
                user.setEmail(registerDTO.getEmail());
                user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                user.setRole(UserType.valueOf(registerDTO.getRole().toUpperCase()));
                user.setIsVerify(false);
                user.setCreatedDate(new Date());
                userRepository.save(user);
                return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, user);
            } catch (IllegalArgumentException e) {
                logger.info("Invalid Role Exception occur");
                throw new InvalidRole("Invalid role: " + registerDTO.getRole() + "." + " valid role is:ADMIN,EMPLOYER,CANDIDATE");
            }
        } else {
            logger.info("Email and Name Already Exist....");
            throw new EmailOrNameAlreadyExistException(ResponseMessage.EMAIL_NAME_ALREADY_EXIST);
        }
    }

    @Override
    public ResponseDTO verifyUser(String email, String password) {
        logger.info("UserServiceImpl : verifyUser");
        User existingData = userRepository.verifyUser(email, password).orElseThrow(() -> new UserNameNotFound(ResponseMessage.USER_NOT_FOUND));
        if (existingData.getIsVerify()) {
            return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ALREADY_VERIFY, null);
        } else {
            existingData.setIsVerify(true);
        }
        userRepository.save(existingData);
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.VERIFY_SUCCESSFULLY, null);
    }

    @Override
    public ResponseDTO loginUser(LoginDTO loginDTO) {
        logger.info("UserServiceImpl : LoginUser");

        User existingData = userRepository.findByName(loginDTO.getName());
        if (existingData == null) {
            throw new CredentialsAreWrongException(ResponseMessage.WRONG_CREDENTIALS);
        }
        if (!existingData.getIsVerify()) {
            throw new NotVerify("Not verify");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), existingData.getPassword())) {
            throw new CredentialsAreWrongException(ResponseMessage.WRONG_CREDENTIALS);
        }
        String token = jwtUtil.generateJwt(loginDTO);

        AuthResponseDTO.AuthDataDTO data = new AuthResponseDTO.AuthDataDTO();
        data.setToken(token);
        data.setName(existingData.getName());
        data.setEmailId(existingData.getEmail());
        data.setRole(existingData.getRole().toString());

        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.LOGIN_SUCCESSFULLY, data);
    }

    @Override
    public ResponseDTO forgetPassword(String email) {
        Optional<User> existingData = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (existingData.isEmpty()) {
            throw new UserNotFound(ResponseMessage.USER_NOT_FOUND);
        }
        User user1 = existingData.get();
        String resetLink = "http://localhost:9999/user/resetPassword?email=" +
                user1.getEmail();
        emailService.sendEmail(user1.getEmail(), "Password Reset Request",
                "Click the link to reset your password: " + resetLink);

        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.EMAIL_SEND_SUCCESSFULLY, null);
    }

    @Override
    public ResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User existingData = userRepository.verifyUser(resetPasswordDTO.getEmail(), resetPasswordDTO.getPassword()).orElseThrow(() -> new UserNameNotFound(ResponseMessage.USER_NOT_FOUND));
        if (existingData != null) {
            existingData.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            userRepository.save(existingData);
            return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.RESET_PASSWORD_SUCCESSFULLY, existingData);
        } else {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, ResponseMessage.OLD_PASS_NOT_MATCH, null);
        }
    }
}