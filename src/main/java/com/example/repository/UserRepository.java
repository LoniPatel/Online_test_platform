package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.name=:name OR u.email=:email")
    User findByNameEmail(String name, String email);

    @Query("select u from User u where u.email=:email AND u.password=:password")
    Optional<User> verifyUser(String email, String password);

    @Query("select u from User u where u.email=:email")
    User findByName(String email);

    @Query(value = "select * from user where role='CANDIDATE' order by created_date DESC",nativeQuery = true)
    List<User> getCandidateList();

    @Query("select u from User u where u.role in ('EMPLOYER','CANDIDATE')")
    List<User> getAllUsers();

    @Query("select u from User u where u.email=:email")
    User findUserByEmail(String email);



}
