package com.atypon.controller;

import com.atypon.model.DatabaseManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class GradesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        DatabaseManager dbManager = new DatabaseManager();
        String grades = dbManager.getGrades(username);

        request.setAttribute("grades", grades);
        RequestDispatcher dispatcher = request.getRequestDispatcher("grades.jsp");
        dispatcher.forward(request, response);
    }
}
