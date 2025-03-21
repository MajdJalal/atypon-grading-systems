package com.atypon.data.model;

import jakarta.persistence.Column;

public class CourseModel {
    private String courseName;

    private int credits;

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }
}
