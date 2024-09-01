package com.example.demo.service;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Courses;
import com.example.demo.repository.CoursesRepo;


@Service
public class CoursesService {
    @Autowired
    private CoursesRepo coursesRepository;

    public String createCourse(Courses course) {
        
         coursesRepository.save(course);
         return "Course added Successfully";
    }
    public Courses updateCourse(int courseId, Courses courseDetails) {
        Courses course = coursesRepository.findById(courseId).orElse(null);
        if (course == null) {
            return null;
        }

        course.setCourseName(courseDetails.getCourseName());
        course.setCategories(courseDetails.getCategories());
        course.setSyllabus(courseDetails.getSyllabus());
        course.setPrerequisites(courseDetails.getPrerequisites());
        course.setDifficultyLevel(courseDetails.getDifficultyLevel());
        course.setSeats(courseDetails.getSeats());
        course.setEnrolled(courseDetails.getEnrolled());
        course.setStartDate(courseDetails.getStartDate());
        course.setEndDate(courseDetails.getEndDate());
        course.setSchedule(courseDetails.getSchedule());

        return coursesRepository.save(course);
    }

    @Transactional
    public String deleteCourse(int courseId) {
        Courses course = coursesRepository.findById(courseId).orElse(null);
        if (course != null) {
            coursesRepository.delete(course);
            return "Course deleted successfully";
        } else {
            return "Course not found";
        }
    }

    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    public List<Courses> getCoursesByName(String courseName) {
        return coursesRepository.findByCourseName(courseName);
    }

    public List<Courses> getCoursesByCategory(String category) {
        return coursesRepository.findByCategory(category);
    }

    public List<Courses> getCoursesByDifficultyLevel(String difficultyLevel) {
        return coursesRepository.findByDifficultyLevel(difficultyLevel);
    }

    public List<Courses> getCoursesByStartDate(LocalDate startDate) {
        return coursesRepository.findByStartDate(startDate);
    }
}
