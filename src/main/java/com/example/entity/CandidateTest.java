package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CandidateTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private String testStatus;

    @ElementCollection
    private List<String> candidateAnswer;

    private Date TestSubmittedDate;
}