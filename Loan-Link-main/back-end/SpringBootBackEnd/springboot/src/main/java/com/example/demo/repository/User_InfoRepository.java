package com.example.demo.repository;

import com.example.demo.entity.User_Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User_InfoRepository extends JpaRepository<User_Info, Long> {
    Optional<User_Info> findByEmail(String email);
    Optional<User_Info> findById(Long id); // Add this method
}
