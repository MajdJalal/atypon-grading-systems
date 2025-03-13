package com.atypon.client;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Client {

    private static String SERVER_ADDRESS;
    private static int PORT;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client() {
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("application-dev.properties")){
            Properties properties = new Properties();
            properties.load(input);
            SERVER_ADDRESS = properties.getProperty("server.socket.address");
            PORT = Integer.parseInt(properties.getProperty("server.socket.port"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not connect to the server!");
        }
    }

    public String receiveMessage() throws IOException {
        return input.readLine();
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public void closeConnection() throws IOException {
        socket.close();
    }
}
