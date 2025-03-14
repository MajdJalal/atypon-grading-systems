package com.atypon.controller;


import com.atypon.model.DatabaseManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/stats")
public class StatsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseManager dbManager = new DatabaseManager();
        String stats = dbManager.getClassStats();

        request.setAttribute("stats", stats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("stats.jsp");
        dispatcher.forward(request, response);
    }
}
