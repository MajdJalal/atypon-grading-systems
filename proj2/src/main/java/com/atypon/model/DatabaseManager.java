package com.atypon.model;


import com.atypon.util.PasswordUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private Connection connection;

    public DatabaseManager() {
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("application-dev.properties")){
            Properties properties = new Properties();
            properties.load(input);
            URL = properties.getProperty("database.url");
            USER = properties.getProperty("database.username");
            PASSWORD = properties.getProperty("database.password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed!");
        }
    }

    public void CreateStudent(String name, String password) throws SQLException {
        String username = name;
        String plainPassword = password;
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        // Insert into the database
        String query = "INSERT INTO students (Username, HashedPassword) VALUES ( ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        stmt.executeUpdate();
    }

    public void createCourse(String courseName) throws SQLException {
        String query = "INSERT INTO courses (CourseName, Credits) VALUES (?, 3)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, courseName);
        stmt.executeUpdate();
    }

    public void assignGrade(String userId, String courseId, Integer grade) throws SQLException {
        String query = "INSERT INTO grades (StudentID, CourseID, Grade) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, userId);
        stmt.setString(2, courseId);
        stmt.setInt(3, grade);
        stmt.executeUpdate();
    }

    public void updateGrade(String userId, String courseId, Integer grade) throws SQLException {
        String query = "UPDATE grades SET Grade = ? WHERE StudentID = ? AND CourseID = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, grade);
        stmt.setString(2, userId);
        stmt.setString(3, courseId);
        stmt.executeUpdate();
    }

    public boolean authenticateUser(String username, String plainPassword) {
        try {
            // Step 1: Retrieve the hashed password from the database
            String query = "SELECT HashedPassword FROM students WHERE Username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("HashedPassword");

                // Step 2: Verify the plain password against the hashed password
                return PasswordUtil.verifyPassword(plainPassword, hashedPassword);
            } else {
                // No user found with the given username
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getGrades(String username) {
        StringBuilder grades = new StringBuilder("Your Grades:\n");
        try {
            String query = "SELECT courses.CourseName, grades.Grade FROM students " +
                    "JOIN grades ON students.StudentID = grades.studentID " +
                    "JOIN courses ON grades.courseID = courses.CourseID " +
                    "WHERE students.Username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                grades.append(rs.getString("CourseName")).append(": ").append(rs.getString("grade")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        grades.append("###");
        return grades.toString();
    }

    public String getGradeForCourse(String username, String courseName) {
        StringBuilder gradeInfo = new StringBuilder("Grade for " + courseName + ":\n");
        try {
            String query = "SELECT courses.CourseName, grades.Grade FROM students " +
                    "JOIN grades ON students.StudentID = grades.studentID " +
                    "JOIN courses ON grades.courseID = courses.CourseID " +
                    "WHERE students.Username = ? AND courses.CourseName = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, courseName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                gradeInfo.append(rs.getString("CourseName")).append(": ").append(rs.getString("Grade")).append("\n");
            } else {
                gradeInfo.append("No grade found for ").append(courseName).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            gradeInfo.append("Error retrieving grade for ").append(courseName).append("\n");
        }
        gradeInfo.append("###");
        return gradeInfo.toString();
    }

    public String getEnrolledCourses(String username) {
        StringBuilder courses = new StringBuilder("Enrolled Courses:\n");
        try {
            String query = "SELECT DISTINCT courses.CourseName FROM students " +
                    "JOIN grades ON grades.StudentID = students.StudentID " +
                    "JOIN courses ON grades.CourseID = courses.CourseID " +
                    "WHERE students.Username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.append(rs.getString("CourseName")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            courses.append("Error retrieving enrolled courses.\n");
        }
        courses.append("###");
        return courses.toString();
    }

    public String getClassStats() {
        StringBuilder stats = new StringBuilder("Class Statistics:\n");

        try {
            //Retrieve all grades from the database
            String query = "SELECT Grade FROM grades";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            //Collect all grades into a list
            List<Integer> grades = new ArrayList<>();
            while (rs.next()) {
                grades.add(rs.getInt("Grade"));
            }

            if (grades.isEmpty()) {
                stats.append("No grades found.\n");
                stats.append("###");
                return stats.toString();
            }

            //Calculate statistics
            // Sort grades for median calculation
            Collections.sort(grades);

            // Calculate average
            double sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            double average = sum / grades.size();

            // Calculate median
            double median;
            int middle = grades.size() / 2;
            if (grades.size() % 2 == 0) {
                median = (grades.get(middle - 1) + grades.get(middle)) / 2.0;
            } else {
                median = grades.get(middle);
            }

            // Calculate highest and lowest
            int highest = Collections.max(grades);
            int lowest = Collections.min(grades);

            //Format the results
            stats.append(String.format("Average Grade: %.2f\n", average));
            stats.append(String.format("Median Grade: %.2f\n", median));
            stats.append(String.format("Highest Grade: %d\n", highest));
            stats.append(String.format("Lowest Grade: %d\n", lowest));

        } catch (SQLException e) {
            e.printStackTrace();
            stats.append("Error retrieving class statistics.\n");
        }

        stats.append("###");
        return stats.toString();
    }
}

