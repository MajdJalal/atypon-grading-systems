package com.atypon.service;

import com.atypon.data.entity.Student;
import com.atypon.data.model.StudentModel;
import com.atypon.data.repository.StudentRepository;
import com.atypon.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public void saveStudent(StudentModel student) {
        String username = student.getUsername();
        String plainPassword = student.getPassword();
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        Student s = new Student();
        s.setUsername(username);
        s.setHashedPassword(hashedPassword);

        studentRepository.save(s);
    }
}