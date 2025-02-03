package com.example.repository;

import com.example.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    @Query(value = "select * from result  where candidate_test_id=:candidateTest", nativeQuery = true)
    List<Result> findCandidateById(Integer candidateTest);
}
