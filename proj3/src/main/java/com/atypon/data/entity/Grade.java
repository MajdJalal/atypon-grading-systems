package com.atypon.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {
    @EmbeddedId
    private StudentCourseId id;

    @JsonBackReference
    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "StudentID")
    private Student student;

    @JsonManagedReference
    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "CourseID")
    private Course course;

    @Column(name = "Grade")
    private int grade;

    // Getters and setters


    public StudentCourseId getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getGrade() {
        return grade;
    }

    public void setId(StudentCourseId id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
