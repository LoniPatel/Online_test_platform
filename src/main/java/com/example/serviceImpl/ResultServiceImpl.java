package com.example.serviceImpl;

import com.example.dto.ResponseDTO;
import com.example.entity.CandidateTest;
import com.example.entity.QueAns;
import com.example.entity.Result;
import com.example.entity.Test;
import com.example.repository.CandidateTestRepository;
import com.example.repository.ResultRepository;
import com.example.service.ResultService;
import com.example.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    public static final Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);
    @Autowired
    ResultRepository resultRepository;
    @Autowired
    CandidateTestRepository candidateTestRepository;
    @Autowired
    EmailService emailService;

    @Override
    public ResponseDTO testResult(Integer id) {
        logger.info("ResultService");
        CandidateTest candidateTest = candidateTestRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidate Not Found"));
        Test test = candidateTest.getTest();
        List<QueAns> questions = test.getQuestionsAns();
        List<String> submittedAnswers = candidateTest.getCandidateAnswer();

        int totalMarks = test.getTotalMarks();
        int obtainMarks =  0;

        for (int i = 0; i < questions.size(); i++) {
            QueAns question = questions.get(i);
            String submittedAns = submittedAnswers.get(i);

            if (question.getAnswer().equalsIgnoreCase(submittedAns)) {
                obtainMarks ++;
            }
        }
        String resultStatus = obtainMarks >= test.getAvgMarksToPass() ? "PASS" : "FAIL";

        Result result = new Result();
        result.setCandidateTest(candidateTest);
        result.setTotalMarks(totalMarks);
        result.setCandidateMarks(obtainMarks);
        result.setResultStatus(resultStatus);
        resultRepository.save(result);

        String candidateEmail = candidateTest.getUser().getEmail();
        String emailSubject = "Test Result Notification";
        String emailBody = String.format("Hii %s,\n\nYour result is as follow:\nTotal Marks: %d\nObtainedMarks:%d\nResult:%s\n\nBest regards"
                ,candidateTest.getUser().getName(), result.getTotalMarks(), result.getCandidateMarks(), result.getResultStatus());
        boolean emailSent = false;
        try {
            emailService.sendEmail(candidateEmail, emailSubject, emailBody);
            emailSent = true;
            logger.info("Email sent Successfully to {}", candidateEmail);
        } catch (Exception e) {
            logger.info("Failed to send email to {}: {}", candidateEmail, e.getMessage());
        }
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE,
                emailSent ? ResponseMessage.ADDED_SUCCESSFULLY + " and email send successfully." : ResponseMessage.ADDED_SUCCESSFULLY + "But failed to send email", result);
    }
}