package com.example.controller;

import com.example.dto.CandidateTestDTO;
import com.example.dto.ResponseDTO;
import com.example.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    @PostMapping("/submitTest")
    public ResponseDTO submitTest(@RequestBody CandidateTestDTO candidateTestDTO) {
        return candidateService.submitTest(candidateTestDTO);
    }
}