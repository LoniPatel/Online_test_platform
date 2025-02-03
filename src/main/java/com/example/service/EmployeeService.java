package com.example.service;

import com.example.dto.ResponseDTO;
import com.example.dto.TestDTO;
import com.example.entity.Test;

import java.io.FileNotFoundException;

public interface EmployeeService {

    ResponseDTO addTest(TestDTO testDTO);

    ResponseDTO deleteTestByEmployer(Integer id);

    ResponseDTO generateExcelSheet() throws FileNotFoundException;
}
