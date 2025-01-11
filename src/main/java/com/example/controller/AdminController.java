package com.example.controller;

import com.example.dto.ResponseDTO;
import com.example.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    public final static Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    AdminService adminService;

    @GetMapping("/getAllUsers")
    public ResponseDTO getAllUsers() {
        logger.info("UserController : getAllUsers");
        return adminService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseDTO getUsersByID(@PathVariable Integer id) {
        logger.info("UserController : getUsersByID");
        return adminService.getUsersByID(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseDTO deleteUsersById(@PathVariable Integer id) {
        return adminService.deleteUsersById(id);
    }

    @GetMapping("/candidates")
    public ResponseDTO getCandidateList() {
        return adminService.getCandidates();
    }
}
