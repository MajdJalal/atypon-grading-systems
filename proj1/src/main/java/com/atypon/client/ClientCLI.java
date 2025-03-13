package com.atypon.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientCLI {
    public static void main(String[] args) {
        Client client = new Client();
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Welcome to the Student Grading System!");
            System.out.print("Enter username:");

            String username = userInput.readLine();
            client.sendMessage(username);

            System.out.print("Enter password:");
            String password = userInput.readLine();
            client.sendMessage(password);

            String response = client.receiveMessage();
            System.out.println(response);
            if (!response.contains("successful")) {
                client.closeConnection();
                return;
            }

            while (true) {
                // Display the menu
                System.out.println("Choose one option:");
                System.out.println("1. View All Grades");
                System.out.println("2. View Grades for a Specific Course");
                System.out.println("3. View Enrolled Courses");
                System.out.println("4. Exit");

                // Read the user's choice
                String choice = userInput.readLine();
                System.out.println("You chose: " + choice);

                // Send the choice to the server
                client.sendMessage(choice);

                // Handle the user's choice
                switch (choice) {
                    case "1": // View All Grades
                        String line;
                        StringBuilder resp = new StringBuilder();
                        while (!(line = client.receiveMessage()).equals("###")) {
                            resp.append(line).append("\n");
                        }
                        System.out.println(resp); // Display grades from the server
                        break;

                    case "2": // View Grades for a Specific Course
                        System.out.print("Enter the course name: ");
                        String courseName = userInput.readLine(); // Read the course name from the user
                        client.sendMessage(courseName); // Send the course name to the server
                        resp = new StringBuilder("");
                        while (!(line = client.receiveMessage()).equals("###")) {
                            resp.append(line).append("\n");
                        }
                        System.out.println(resp); // Display grades from the server
                        break;

                    case "3": // View Enrolled Courses
                        resp = new StringBuilder("");
                        while (!(line = client.receiveMessage()).equals("###")) {
                            resp.append(line).append("\n");
                        }
                        System.out.println(resp); // Display grades from the server
                        break;

                    case "4": // Exit
                        System.out.println("Goodbye!");
                        break; // Exit the loop and end the program

                    default: // Invalid option
                        System.out.println("Invalid option. Try again.");
                        break;
                }
                if(choice.equals("4"))
                    break;
            }
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
