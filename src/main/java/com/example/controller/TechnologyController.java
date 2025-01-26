package com.example.controller;

import com.example.dto.ResponseDTO;
import com.example.dto.TechnologyDTO;
import com.example.service.TechnologyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tech")
public class TechnologyController {
    public final static Logger logger = LoggerFactory.getLogger(TechnologyController.class);
    @Autowired
    TechnologyService technologyService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addTechnology")
    public ResponseDTO addTechnology(@RequestBody TechnologyDTO technologyDTO) {
        logger.info("TechnologyController : addTechnology");
        return technologyService.addTechnology(technologyDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteTechById(@PathVariable Integer id) {
        technologyService.deleteTechByAdmin(id);
    }
}