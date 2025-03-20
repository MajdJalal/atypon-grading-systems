package com.atypon.controller;

import com.atypon.data.entity.Course;
import com.atypon.data.entity.Student;
import com.atypon.data.model.CourseModel;
import com.atypon.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listCourses(Model model) {
        final List<Course> allCourses = courseService.getAllCourses();
        model.addAttribute("courses", allCourses);
        return "courses"; // Renders courses.html
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Course getCourse(@PathVariable("id") Long id) {
        final Optional<Course> courseById = courseService.getCourseById(id);
        return courseById
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @PostMapping(consumes = "application/json")
    public String addCourse(@RequestBody CourseModel course) {
        courseService.saveCourse(course);
        return "redirect:/courses"; // Redirects to the list of courses
    }
}