package com.example.serviceImpl;

import com.example.dto.QueAnsDTO;
import com.example.dto.ResponseDTO;
import com.example.dto.TestDTO;
import com.example.dto.TestResponseDTO;
import com.example.entity.QueAns;
import com.example.entity.Technology;
import com.example.entity.Test;
import com.example.entity.User;
import com.example.enums.UserType;
import com.example.exception.SomeIdsNotFound;
import com.example.exception.UserNameNotFound;
import com.example.repository.QueAnsRepository;
import com.example.repository.TechnologyRepository;
import com.example.repository.TestRepository;
import com.example.repository.UserRepository;
import com.example.service.EmployeeService;
import com.example.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployeeService {
    public static final Logger logger = LoggerFactory.getLogger(EmployerServiceImpl.class);

    @Autowired
    TestRepository testRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    QueAnsRepository queAnsRepository;

    @Override
    public ResponseDTO addTest(TestDTO testDTO) {
        Test test = new Test();
        Optional<User> user = userRepository.findById(testDTO.getUserId());
        if (user.isPresent() && user.get().getRole().equals(UserType.EMPLOYER)) {

//            boolean textExist = testRepository.existByUserId(testDTO.getUserId());
//            if (textExist) {
//                throw new UserAlreadyExist(ResponseMessage.USER_ALREADY_EXIST);
//            }
            //Save testEntity Details
            test.setUser(user.get());
            test.setName(testDTO.getName());
            test.setDuration(testDTO.getDuration());
            test.setTotalMarks(testDTO.getTotalMarks());
            test.setAvgMarksToPass(testDTO.getAvgMarksToPass());
            test.setCreatedDate(new Date());
        } else {
            throw new UserNameNotFound(ResponseMessage.UNAUTHORIZED_USER_ROLE);
        }
        TestResponseDTO testResponseDTO = new TestResponseDTO();
        testResponseDTO.setName(test.getName());
        testResponseDTO.setDuration(test.getDuration());
        testResponseDTO.setTotalMarks(test.getTotalMarks());
        testResponseDTO.setAvgMarksToPass(test.getAvgMarksToPass());
        testResponseDTO.setCreatedDate(test.getCreatedDate());
        testResponseDTO.setQuestionsAns(test.getQuestionsAns());

        //Set Technology Id
        List<Technology> technologies = new ArrayList<>();
        if (testDTO.getTechIds() != null && !testDTO.getTechIds().isEmpty()) {
            technologies = technologyRepository.findTechById(testDTO.getTechIds());
            if (technologies.size() < testDTO.getTechIds().size()) {
                throw new SomeIdsNotFound(ResponseMessage.SOME_IDS_NOT_FOUND);
            }
        }
        test.setTechnologies(technologies);
        testRepository.save(test);


        List<String> techNames = new ArrayList<>();
        for (Technology technology : technologies) {
            techNames.add(technology.getName());
        }
        testResponseDTO.setTechName(techNames);

        //setQueAns Details
        if (test.getId() != 0) {
            List<QueAns> queAnsList = new ArrayList<>();
            for (QueAnsDTO queAnsDTO : testDTO.getQuestionsAns()) {
                QueAns queAns = new QueAns();
                queAns.setQuestion(queAnsDTO.getQuestion());
                queAns.setAnswer(queAnsDTO.getAnswer());
                queAnsList.add(queAns);
            }
            test.setQuestionsAns(queAnsList);
            queAnsRepository.saveAll(queAnsList);
            testResponseDTO.setQuestionsAns(queAnsList);
        }
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, testResponseDTO);
    }

    @Override
    public ResponseDTO deleteTestByEmployer(Integer id) {
        Optional<Test> test = testRepository.findById(id);
        if (test.isPresent()) {
            testRepository.deleteById(id);
            return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.DELETE_SUCCESSFULLY, null);
        } else {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, ResponseMessage.ID_IS_NOT_PRESENT, null);
        }
    }
}