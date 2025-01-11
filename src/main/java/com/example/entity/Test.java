package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @ElementCollection
    private List<Technology> technologies;

    private String name;

    private String duration;

    private Integer totalMarks;

    private Integer avgMarksToPass;

    private Date createdDate;

    @ElementCollection
    // @OneToMany(mappedBy = "test",cascade = CascadeType.ALL)
    private List<QueAns> questionsAns;
}