package com.example.service;

import com.example.dto.CandidateTestDTO;
import com.example.dto.ResponseDTO;

public interface CandidateService {


    ResponseDTO submitTest(CandidateTestDTO candidateDTO);
}
