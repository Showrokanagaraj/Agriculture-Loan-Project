package com.example.demo.controller;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Courses;
import com.example.demo.service.CoursesService;

@RestController
public class CoursesController {
    @Autowired
    private CoursesService coursesService;
    @PostMapping("/admin/courses/create")
    public ResponseEntity<String> createCourse(@RequestBody Courses course) {
        String message = coursesService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/admin/courses/update/{courseId}")
    public ResponseEntity<Courses> updateCourse(@PathVariable int courseId, @RequestBody Courses courseDetails) {
        Courses updatedCourse = coursesService.updateCourse(courseId, courseDetails);
        if (updatedCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/admin/courses/delete/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable int courseId) {
        String message = coursesService.deleteCourse(courseId);
        if (message.equals("Course deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @GetMapping("/adminuser/courses/list")
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = coursesService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/adminuser/courses/search/name")
    public ResponseEntity<List<Courses>> getCoursesByName(@RequestParam String courseName) {
        List<Courses> courses = coursesService.getCoursesByName(courseName);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/adminuser/courses/category")
    public ResponseEntity<List<Courses>> getCoursesByCategory(@RequestParam String category) {
        List<Courses> courses = coursesService.getCoursesByCategory(category);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/adminuser/courses/search/difficulty")
    public ResponseEntity<List<Courses>> getCoursesByDifficultyLevel(@RequestParam String difficultyLevel) {
        List<Courses> courses = coursesService.getCoursesByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/adminuser/courses/search/start-date")
    public ResponseEntity<List<Courses>> getCoursesByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<Courses> courses = coursesService.getCoursesByStartDate(startDate);
        return ResponseEntity.ok(courses);
    }
}
