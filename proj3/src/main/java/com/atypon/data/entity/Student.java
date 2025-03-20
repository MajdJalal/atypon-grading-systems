package com.atypon.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private Long studentId;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "EnrollmentDate")
    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;

    @Column(name = "HashedPassword", nullable = false)
    private String hashedPassword;

    @Column(name = "role")
    private String role;

    @JsonManagedReference
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Grade> grades;

    // Getters and setters

    public Long getStudentId() {
        return studentId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public String getRole() {
        return role;
    }
}