package com.au.health.controller;

import com.au.health.dao.AppointmentDAO;
import com.au.health.model.Appointment;
import com.au.health.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AppointmentServlet", urlPatterns = {"/AppointmentServlet"})
public class AppointmentServlet extends HttpServlet {

    private final AppointmentDAO dao = new AppointmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("authenticatedUser") : null;

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        if ("STAFF".equals(user.getRole())) {
            req.setAttribute("appointments", dao.getAllAppointments());
            req.getRequestDispatcher("staff_portal.jsp").forward(req, res);
        } else {
            req.setAttribute("appointments", dao.getAppointmentsByPatient(user.getUsername()));
            req.getRequestDispatcher("patient_dashboard.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("authenticatedUser") : null;

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if ("book".equals(action) && "PATIENT".equals(user.getRole())) {
            String department = req.getParameter("department");
            String date = req.getParameter("appointmentDate");
            String symptoms = req.getParameter("symptoms");

            if (department != null && date != null && symptoms != null && !symptoms.trim().isEmpty()) {
                Appointment app = new Appointment();
                app.setPatientUsername(user.getUsername());
                app.setDepartment(department.trim());
                app.setAppointmentDate(date.trim());
                app.setSymptoms(symptoms.trim());
                dao.createAppointment(app);
            }
        } else if ("updateStatus".equals(action) && "STAFF".equals(user.getRole())) {
            String rawId = req.getParameter("appointmentId");
            String newStatus = req.getParameter("status");
            if (rawId != null && newStatus != null) {
                try {
                    int appId = Integer.parseInt(rawId.trim());
                    dao.updateStatus(appId, newStatus.trim());
                } catch (NumberFormatException ignored) {
                }
            }
        }

        res.sendRedirect("AppointmentServlet");
    }
}