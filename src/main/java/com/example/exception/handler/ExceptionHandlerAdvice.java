package com.example.exception.handler;

import com.example.dto.ResponseDTO;
import com.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO EmailOrNameAlreadyExist(EmailOrNameAlreadyExistException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO UserNameNotFound(UserNameNotFound exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO CredentialsAreWrongException(CredentialsAreWrongException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO InvalidRole(InvalidRole exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO InvalidRole(SomeIdsNotFound exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    public ResponseDTO UserAlreadyExist(UserAlreadyExist exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.ALREADY_REPORTED.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO UserRoleIsNotSufficient(UserRoleIsNotSufficient exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO UserRoleIsNotSufficient(NotVerify exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO QuestionNotFound(QuestionNotFound exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO UserNotFound(UserNotFound exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }
}
