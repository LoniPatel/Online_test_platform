package com.example.controller;

import com.example.dto.ResponseDTO;
import com.example.dto.TestDTO;
import com.example.service.EmployeeService;
import com.example.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    public final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    @PostMapping("/addTest")
    public ResponseDTO addTest(@RequestBody TestDTO testDTO) {
        logger.info("EmployeeController : addTest");
        return employeeService.addTest(testDTO);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    @DeleteMapping("/{id}")
    public ResponseDTO deleteTestByEmployerId(@PathVariable Integer id) {
        return employeeService.deleteTestByEmployer(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/generate-excel")
    public ResponseDTO generateTestExcel() {
        logger.info("EmployerController : generateTestExcel");
        try {
            return employeeService.generateExcelSheet();
        } catch (Exception e) {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, "Failed to generate Excel: " + e.getMessage(), null);
        }
    }
}