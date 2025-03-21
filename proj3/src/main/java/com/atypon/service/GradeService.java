package com.atypon.service;

import com.atypon.data.entity.Grade;
import com.atypon.data.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getAllStudentCourses() {
        return gradeRepository.findAll();
    }
}