package com.atypon.controller;

import com.atypon.data.entity.Grade;
import com.atypon.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public String getGrades(Model model) {
        final List<Grade> allStudentCourses = gradeService.getAllStudentCourses();
        model.addAttribute("grades", allStudentCourses);
        return "grades";
    }
}