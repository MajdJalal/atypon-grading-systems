package com.atypon.admin;

import com.atypon.database.DatabaseManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Admin {
    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.println("Choose one option:");
            System.out.println("1. create a student");
            System.out.println("2. create a course");
            System.out.println("3. assign a grade");
            System.out.println("4. update a grade");
            System.out.println("5. exit");
            String choice = userInput.readLine();
            switch (choice) {
                case "1":
                    System.out.println("Username: ");
                    String username = userInput.readLine();
                    System.out.println("Password: ");
                    String password = userInput.readLine();
                    createStudent(username, password);
                    break;

                case "2": //
                    System.out.println("CourseName: ");
                    String courseName = userInput.readLine();
                    createCourse(courseName);
                    break;

                case "3":
                    System.out.println("userId: ");
                    String userId = userInput.readLine();
                    System.out.println("CourseId: ");
                    String courseId = userInput.readLine();
                    System.out.println("Grade: ");
                    String grade = userInput.readLine();
                    assignGrade(userId, courseId, Integer.valueOf(grade));
                    break;

                case "4" :
                    System.out.println("userId: ");
                    String userId2 = userInput.readLine();
                    System.out.println("CourseId: ");
                    String courseId2 = userInput.readLine();
                    System.out.println("Grade: ");
                    String grade2 = userInput.readLine();
                    updateGrade(userId2, courseId2, Integer.valueOf(grade2));
                    break;

                case"5":
                    break;

                default: // Invalid option
                    System.out.println("Invalid option. Try again.");
                    break;
            }
            if(choice.equals("5"))
                break;
        }

    }

    private static void createStudent(String username, String password) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.CreateStudent(username, password);
    }
    private static void createCourse(String courseName) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.createCourse(courseName);
    }
    private static void assignGrade(String studentId, String courseId, Integer grade) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.assignGrade(studentId, courseId, grade);
    }
    private static void updateGrade(String studentId, String courseId, Integer grade) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.updateGrade(studentId, courseId, grade);
    }
}

