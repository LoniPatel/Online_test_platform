//package com.example.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Entity
//public class AttemptedQuestion {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    private CandidateTest candidateTest;
//
//    private String question;
//
//    private String candidateAnswer;
//
//    private boolean isCorrect;
//}
