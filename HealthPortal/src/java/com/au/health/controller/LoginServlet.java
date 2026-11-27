package com.au.health.controller;

import com.au.health.dao.AppointmentDAO;
import com.au.health.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private final AppointmentDAO dao = new AppointmentDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = dao.authenticate(username, password);
        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("authenticatedUser", user);
            res.sendRedirect("AppointmentServlet");
        } else {
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        res.sendRedirect("login.jsp");
    }
}