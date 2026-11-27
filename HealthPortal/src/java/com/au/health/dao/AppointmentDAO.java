package com.au.health.dao;

import com.au.health.model.Appointment;
import com.au.health.model.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    private static final String DB_URL = "jdbc:derby://localhost:1527/health_portal_db";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "app";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public User authenticate(String username, String plainPassword) {
        if (username == null || plainPassword == null) {
            return null;
        }
        String sql = "SELECT user_id, username, full_name, role FROM users WHERE username = ? AND password_hash = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ps.setString(2, hashPassword(plainPassword));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("role")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createAppointment(Appointment app) {
        String sql = "INSERT INTO appointments (patient_username, department, appointment_date, symptoms, status) VALUES (?, ?, ?, ?, 'PENDING')";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, app.getPatientUsername());
            ps.setString(2, app.getDepartment());
            ps.setString(3, app.getAppointmentDate());
            ps.setString(4, app.getSymptoms());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAppointmentsByPatient(String username) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, patient_username, department, appointment_date, symptoms, status FROM appointments WHERE patient_username = ? ORDER BY appointment_id DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getString("patient_username"),
                        rs.getString("department"),
                        rs.getString("appointment_date"),
                        rs.getString("symptoms"),
                        rs.getString("status")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, patient_username, department, appointment_date, symptoms, status FROM appointments ORDER BY appointment_id DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getString("patient_username"),
                    rs.getString("department"),
                    rs.getString("appointment_date"),
                    rs.getString("symptoms"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int appointmentId, String newStatus) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}