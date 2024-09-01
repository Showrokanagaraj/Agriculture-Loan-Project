package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Courses;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoursesRepo extends JpaRepository<Courses, Integer> {
    List<Courses> findByCourseName(String courseName);
    List<Courses> findByDifficultyLevel(String difficultyLevel);
    List<Courses> findByStartDate(LocalDate startDate);
    @Query("SELECT c FROM Courses c JOIN c.categories cat WHERE cat = :category")
    List<Courses> findByCategory(@Param("category") String category);
}
