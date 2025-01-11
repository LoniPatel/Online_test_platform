package com.example.serviceImpl;

import com.example.dto.ResponseDTO;
import com.example.entity.User;
import com.example.enums.UserType;
import com.example.exception.UserRoleIsNotSufficient;
import com.example.repository.UserRepository;
import com.example.service.AdminService;
import com.example.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    public final static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseDTO getAllUsers() {
        logger.info("UserServiceImpl : getAllUsers");
        List<User> userList = userRepository.getAllUsers();
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCH_SUCCESSFULLY, userList);
    }

    @Override
    public ResponseDTO getUsersByID(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, ResponseMessage.ID_IS_NOT_PRESENT, null);
        }
        if (user.get().getRole().equals(UserType.EMPLOYER)) {
            return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCH_SUCCESSFULLY, user);
        } else {
            throw new UserRoleIsNotSufficient(ResponseMessage.ID_ROLE_NOT_USER);
        }
    }

    @Override
    public ResponseDTO deleteUsersById(Integer id) {
        userRepository.deleteById(id);
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.DELETE_SUCCESSFULLY, null);
    }

    @Override
    public ResponseDTO getCandidates() {
        List<User> userList = userRepository.getCandidateList();
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCH_SUCCESSFULLY, userList);
    }
}