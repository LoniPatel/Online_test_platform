package com.example.repository;

import com.example.entity.QueAns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueAnsRepository extends JpaRepository<QueAns, Integer> {
}