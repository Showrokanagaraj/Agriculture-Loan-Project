package com.example.demo.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "courses")
public class Courses  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false, updatable = false)
    private int courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @ElementCollection
    @CollectionTable(name = "course_categories", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "category", nullable = false)
    private List<String> categories = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "course_syllabus", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "syllabus_item", nullable = false)
    private List<String> syllabus = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "course_prerequisites", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "prerequisite", nullable = false)
    private List<String> prerequisites = new ArrayList<>();

    @Column(name = "difficulty_level", nullable = false)
    private String difficultyLevel;

    @Column(name = "seats", nullable = false)
    private int seats;

    @Column(name = "enrolled", nullable = false)
    private int enrolled;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ElementCollection
    @CollectionTable(name = "course_schedule", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "schedule_item", nullable = false)
    private List<String> schedule = new ArrayList<>();
}
