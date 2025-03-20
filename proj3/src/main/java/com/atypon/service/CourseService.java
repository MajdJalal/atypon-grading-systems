package com.atypon.service;

import com.atypon.data.entity.Course;
import com.atypon.data.model.CourseModel;
import com.atypon.data.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void saveCourse(CourseModel course) {
        Course c = new Course();
        c.setCourseName(course.getCourseName());
        c.setCredits(course.getCredits());
        courseRepository.save(c);
    }
}
