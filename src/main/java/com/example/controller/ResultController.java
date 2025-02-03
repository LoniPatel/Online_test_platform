package com.example.controller;

import com.example.dto.ResponseDTO;
import com.example.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result")
public class ResultController {

    @Autowired
    ResultService resultService;

    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    @GetMapping("/{id}")
    public ResponseDTO createResult(@PathVariable Integer id) {
        return resultService.testResult(id);
    }
}