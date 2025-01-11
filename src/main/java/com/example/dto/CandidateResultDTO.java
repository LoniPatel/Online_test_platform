package com.example.dto;

import com.example.entity.QueAns;
import com.example.entity.Test;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CandidateResultDTO {

    private Integer test;

    private List<QueAns> queId;

    private List<String> candidateAnswer;
}
