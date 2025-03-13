package com.atypon.server;

import com.atypon.database.DatabaseManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server {
    private static int PORT;
    private static DatabaseManager dbManager;

    static {
        try(InputStream input = Server.class.getClassLoader().getResourceAsStream("application-dev.properties")){
            Properties properties = new Properties();
            properties.load(input);
            PORT = Integer.parseInt(properties.getProperty("server.socket.port"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        dbManager = new DatabaseManager();  // Initialize database connection

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Pass dbManager to handle database operations
                new ClientHandler(clientSocket, dbManager).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
