package com.au.health.model;

public class Appointment {
    private int appointmentId;
    private String patientUsername;
    private String department;
    private String appointmentDate;
    private String symptoms;
    private String status;

    public Appointment() {
    }

    public Appointment(int appointmentId, String patientUsername, String department,
                       String appointmentDate, String symptoms, String status) {
        this.appointmentId = appointmentId;
        this.patientUsername = patientUsername;
        this.department = department;
        this.appointmentDate = appointmentDate;
        this.symptoms = symptoms;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}