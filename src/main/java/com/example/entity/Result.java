package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @OneToOne
//    private Test test;
    @OneToOne
    private CandidateTest candidateTest;

    private Integer totalMarks;

    private Integer candidateMarks;

    private String resultStatus;
}