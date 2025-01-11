//package com.example.serviceImpl;
//
//import com.example.dto.CandidateTestDTO;
//import com.example.dto.ResponseDTO;
//import com.example.entity.CandidateTest;
//import com.example.entity.Test;
//import com.example.entity.User;
//import com.example.enums.UserType;
//import com.example.repository.CandidateTestRepository;
//import com.example.repository.TestRepository;
//import com.example.repository.UserRepository;
//import com.example.service.CandidateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CandidateTestServiceImpl implements CandidateService {
////    @Autowired
////    CandidateTestRepository candidateTestRepository;
////    @Autowired
////    UserRepository userRepository;
////    @Autowired
////    TestRepository testRepository;
////
////    @Override
////    public ResponseDTO submitTest(CandidateTestDTO candidateDTO) {
//////        User user = userRepository.findById(candidateDTO.getUserId()).orElseThrow(() -> new RuntimeException("Candidate not found"));
//////        if (!user.getRole().equals(UserType.CANDIDATE)) {
//////            throw new RuntimeException("User is not Candidate");
//////        }
//////        Test test = testRepository.findById(candidateDTO.getTestId()).orElseThrow(() -> new RuntimeException("Test is not found"));
//////        CandidateTest candidateTest = new CandidateTest();
//////        candidateTest.setUser(user);
//////        candidateTest.setTest(test);
//////
//////        candidateTestRepository.save(candidateTest);
////
////        return null;
////    }
////
//////    @Override
//////    public ResponseDTO submitTest(CandidateTestDTO candidateTestDTO) {
//////        User candidate = userRepository.findById(candidateTestDTO.getCandidateId()).orElseThrow(() -> new RuntimeException("Candidate not found"));
//////        if (!candidate.getRole().equals(UserType.CANDIDATE)) {
//////            throw new RuntimeException("User is not a Candidate");
//////        }
//////        Test test = testRepository.findById(candidateTestDTO.getTestId()).orElseThrow(() -> new RuntimeException("Test is not found"));
//////        CandidateTest candidateTest = new CandidateTest();
//////        candidateTest.setCandidateId(candidate);
//////        candidateTest.setTest(test);
//////
//////        candidateTest = candidateTestRepository.save(candidateTest);
//////
//////
//////        int obtainedMarks = 0;
//////
//////        List<AttemptedQuestion> attemptedQuestionList = new ArrayList<>();
//////        for (AttemptedQuestionDTO attemptedQuestionDTO : candidateTestDTO.getAttemptedQuestionList()) {
//////            AttemptedQuestion attemptedQuestion = new AttemptedQuestion();
//////            attemptedQuestion.setCandidateTest(candidateTest);
//////            attemptedQuestion.setQuestion(attemptedQuestionDTO.getQuestion());
//////            attemptedQuestion.setCandidateAnswer(attemptedQuestionDTO.getCandidateAnswer());
//////            boolean isCorrect = false;
//////
//////            for (QueAns queAns : test.getQuestionsAns()) {
//////                if (queAns.getQuestion().equals(attemptedQuestionDTO.getQuestion())
//////                        && queAns.getAnswer().equals(attemptedQuestionDTO.getCandidateAnswer())) {
//////                    isCorrect = true;
//////                    break;
//////                }
//////
//////            }
//////            attemptedQuestion.setCorrect(isCorrect);
//////            if (isCorrect) {
//////                obtainedMarks++;
//////            }
//////            attemptedQuestionList.add(attemptedQuestion);
//////        }
//////        if (!attemptedQuestionList.isEmpty()){
//////            attemptedQuestionRepository.saveAll(attemptedQuestionList);
//////            candidateTest.setTestStatus("Submitted");
//////        }
//////
//////       // candidateTest.setAttemptedQues(attemptedQuestionList);
//////        candidateTestRepository.save(candidateTest);
//////        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.TEST_SUBMIT_SUCCESSFULLY, candidateTest);
//////    }
//}