package com.atypon.controller;

import com.atypon.data.entity.Grade;
import com.atypon.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("atypon/student-courses")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public String listStudentCourses(Model model) {
        model.addAttribute("studentCourses", gradeService.getAllStudentCourses());
        return "student-courses"; // Renders student-courses.html
    }

    @PostMapping
    public String addStudentCourse(@ModelAttribute Grade grade) {
        gradeService.saveStudentCourse(grade);
        return "redirect:/student-courses"; // Redirects to the list of student-courses
    }
}