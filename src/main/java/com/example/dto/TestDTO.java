package com.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestDTO {

    private Integer userId;

    private String name;

    private String duration;

    private Integer totalMarks;

    private Integer avgMarksToPass;

    private List<Integer> techIds;

    private List<QueAnsDTO> questionsAns;
}
