package com.atypon.server;

import com.atypon.database.DatabaseManager;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private DatabaseManager dbManager;

    public ClientHandler(Socket socket, DatabaseManager dbManager) {
        this.socket = socket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String username = input.readLine();
            String password = input.readLine();

            if (dbManager.authenticateUser(username, password)) {
                output.println("Login successful!");
                while (true) {
                    String choice = input.readLine(); // Read the client's choice
                    System.out.println("Client chose: " + choice); // Log the choice for debugging

                    switch (choice) {
                        case "1": // View All Grades
                            output.println(dbManager.getGrades(username));
                            break;

                        case "2": // View Grades for a Specific Course
                            String courseName = input.readLine(); // Read the course name from the client
                            System.out.println("Client requested grade for course: " + courseName);
                            output.println(dbManager.getGradeForCourse(username, courseName));
                            break;

                        case "3": // View Enrolled Courses
                            output.println(dbManager.getEnrolledCourses(username));
                            break;

                        case "4": // Exit
                            output.println("Goodbye!");
                            break;

                        default: // Invalid option
                            output.println("Invalid option. Try again.");
                            break;
                    }

                    if ("4".equals(choice)) {
                        break;
                    }
                }
            } else {
                output.println("Invalid login. Disconnecting.");
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
