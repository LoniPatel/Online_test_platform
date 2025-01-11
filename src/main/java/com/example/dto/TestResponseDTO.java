package com.example.dto;

import com.example.entity.QueAns;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestResponseDTO {

    private String name;

    private String duration;

    private Integer totalMarks;

    private Integer avgMarksToPass;

    private Date createdDate;

    private List<String> techName;

    private List<QueAns> questionsAns;
}