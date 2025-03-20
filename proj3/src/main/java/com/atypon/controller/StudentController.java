package com.atypon.controller;

import com.atypon.data.entity.Student;
import com.atypon.data.model.StudentModel;
import com.atypon.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students"; // Renders students.html
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Student getStudent(@PathVariable("id") Long id) {
        final Optional<Student> studentById = studentService.getStudentById(id);
        return studentById
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PostMapping(consumes = "application/json")
    public String addStudent(@RequestBody StudentModel student) {
        studentService.saveStudent(student);
        return "redirect:/students"; // Redirects to the list of students
    }
}