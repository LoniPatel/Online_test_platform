package com.example.dto;

import com.example.entity.QueAns;
import com.example.entity.Test;
import lombok.*;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CandidateTestDTO {

    private Integer userId;

    private Integer testId;

    private List<Integer> queId;

    private List<String> candidateAnswer;

}