package com.example.service;

import com.example.dto.ResponseDTO;
import com.example.dto.TechnologyDTO;

public interface TechnologyService {

    ResponseDTO addTechnology(TechnologyDTO technologyDTO);

    void deleteTechByAdmin(Integer id );

}