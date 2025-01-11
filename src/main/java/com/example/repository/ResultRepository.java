package com.example.repository;

import com.example.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
//
//    @Query("select r from Result r where r.candidateId=:id")
//    Result findCandidateById(Integer candidateId);

    @Query(value = "select * from result  where candidate_test_id=:candidateTest",nativeQuery = true)
    Optional<Result> findCandidateById(Integer candidateTest);
}
