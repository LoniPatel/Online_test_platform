package com.example.service;

import com.example.dto.ResponseDTO;

public interface AdminService {

    ResponseDTO getAllUsers();

    ResponseDTO getUsersByID(Integer id);

    ResponseDTO deleteUsersById(Integer id);

    ResponseDTO getCandidates();
}
