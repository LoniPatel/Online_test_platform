package com.example.serviceImpl;

import com.example.dto.CandidateTestDTO;
import com.example.dto.ResponseDTO;
import com.example.entity.CandidateTest;
import com.example.entity.QueAns;
import com.example.entity.Test;
import com.example.entity.User;
import com.example.enums.UserType;
import com.example.exception.InvalidRole;
import com.example.repository.CandidateTestRepository;
import com.example.repository.QueAnsRepository;
import com.example.repository.TestRepository;
import com.example.repository.UserRepository;
import com.example.service.CandidateService;
import com.example.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    CandidateTestRepository candidateTestRepository;
    @Autowired
    QueAnsRepository queAnsRepository;

    @Override
    public ResponseDTO submitTest(CandidateTestDTO candidateDTO) {
        User user = userRepository.findById(candidateDTO.getUserId()).orElseThrow(() -> new RuntimeException("User ot found"));

        if (!user.getRole().equals(UserType.CANDIDATE)) {
            throw new InvalidRole(ResponseMessage.UNAUTHORIZED_USER_ROLE);
        }

        Test test = testRepository.findById(candidateDTO.getTestId()).orElseThrow(() -> new RuntimeException("Test not found"));

        List<Integer> questionIds = candidateDTO.getQueId();
        List<QueAns> questions = queAnsRepository.findAllById(questionIds);

        if (questions.size() != questionIds.size()) {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, ResponseMessage.QUESTION_NOT_FOUND, null);
        }
        if (questionIds.size() != candidateDTO.getCandidateAnswer().size()) {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, ResponseMessage.QUE_ANS_NOT_MATCH, null);
        }

        CandidateTest candidateTest = new CandidateTest();
        candidateTest.setUser(user);
        candidateTest.setTest(test);
        candidateTest.setTestStatus("Submitted");
        candidateTest.setCandidateAnswer(candidateDTO.getCandidateAnswer());
        candidateTest.setTestSubmittedDate(new Date());

        candidateTestRepository.save(candidateTest);
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.TEST_SUBMIT_SUCCESSFULLY, candidateTest);
    }
}
