package com.atypon.data.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentCourseId implements Serializable {
    private Long studentId;
    private Long courseId;

    // Getters, setters, equals, and hashCode
}