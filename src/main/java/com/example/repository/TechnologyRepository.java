package com.example.repository;

import com.example.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {

   @Query("select t from Technology t where t.name IN :name")
   List<Technology> findTechByName(List<String> name);

   @Query("select t from Technology t where t.id IN :id")
   List<Technology> findTechById(List<Integer> id);
}