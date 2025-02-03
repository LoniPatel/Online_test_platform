package com.example.serviceImpl;

import com.example.dto.QueAnsDTO;
import com.example.dto.ResponseDTO;
import com.example.dto.TestDTO;
import com.example.dto.TestResponseDTO;
import com.example.entity.*;
import com.example.enums.UserType;
import com.example.exception.SomeIdsNotFound;
import com.example.exception.UserNameNotFound;
import com.example.repository.*;
import com.example.service.EmployeeService;
import com.example.util.ResponseMessage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    ResultRepository resultRepository;

    @Override
    public ResponseDTO addTest(TestDTO testDTO) {
        Test test = new Test();
        Optional<User> user = userRepository.findById(testDTO.getUserId());
        if (user.isPresent() && user.get().getRole().equals(UserType.EMPLOYER)) {

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

    @Override
    public ResponseDTO generateExcelSheet() throws FileNotFoundException {
        File directory = new File("C:\\Reports");
        if (!directory.exists()) {
            directory.mkdirs(); // Creates the directory if it doesn't exist
        }
        String filePath = "C:\\Reports\\TestDetails.xlsx";
        File file = new File(filePath);
        List<Test> tests = testRepository.findAll();

        if (tests.isEmpty()) {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, ResponseMessage.NO_TEST_FOUND, null);
        }
        Workbook workbook;
        Sheet sheet;
        try {
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                    sheet = workbook.getSheetAt(0);
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Test Details");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"TestId", "TestName", "TechnologyName",
                        "TotalTestMark", "EmployerName", "EmployerEmail",
                        "CandidateName", "CandidateMarks", "CandidateResult Status",
                        "TestSubmittedDate"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(createHeaderCellStyle(workbook));
                }
            }
            // Fetch existing Test IDs from the Excel sheet
            Set<Integer> existingTestIds = new HashSet<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        existingTestIds.add((int) cell.getNumericCellValue());
                    }
                }
            }
            int currentRowNum = sheet.getLastRowNum() + 1;
            for (Test test : tests) {
                if (!existingTestIds.contains(test.getId())) {
                    User user = userRepository.findById(test.getUser().getId()).orElseThrow(() -> new RuntimeException("Employer not found"));
                    String technologyNames = test.getTechnologies().stream()
                            .map(Technology::getName) // Assuming Technology has a getName() method
                            .collect(Collectors.joining(", "));

                    List<Result> results = resultRepository.findCandidateById(test.getId());
                    if (results.isEmpty()) {
                        Row row = sheet.createRow(currentRowNum++);
                        row.createCell(0).setCellValue(test.getId());
                        row.createCell(1).setCellValue(test.getName());
                        row.createCell(2).setCellValue(technologyNames);
                        row.createCell(3).setCellValue(test.getTotalMarks());
                        row.createCell(4).setCellValue(user.getName());
                        row.createCell(5).setCellValue(user.getEmail());
                        // row.createCell(7).setCellValue("N/A");
                    } else {
                        for (Result result : results) {
                            User candidate = result.getCandidateTest().getUser();

                            Row row = sheet.createRow(currentRowNum++);
                            row.createCell(0).setCellValue(test.getId());
                            row.createCell(1).setCellValue(test.getName());
                            row.createCell(2).setCellValue(technologyNames);
                            row.createCell(3).setCellValue(test.getTotalMarks());
                            row.createCell(4).setCellValue(user.getName());
                            row.createCell(5).setCellValue(user.getEmail());
                            row.createCell(6).setCellValue(candidate.getName());
                            row.createCell(7).setCellValue(result.getCandidateMarks());
                            row.createCell(8).setCellValue(result.getResultStatus());
                            Cell cell = row.createCell(9);
                            cell.setCellValue(candidate.getCreatedDate());
                            cell.setCellStyle(createHeaderCellStyle(workbook));
                        }
                    }
                }
            }
            //Increase size
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }
            //Save file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            } finally {
                workbook.close();
            }

        } catch (IOException e) {
            throw new RuntimeException("Fail to generate Excel file at: " + e);
        }
        return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, "Excel file generated Successfully", filePath);
    }


    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        CreationHelper creationHelper = workbook.getCreationHelper();
        style.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        return style;
    }
}
