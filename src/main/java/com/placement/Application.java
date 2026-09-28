package com.placement;

import java.io.Serializable;

public class Application implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String company;
    private String role;
    private String applicationDate;
    private String status;
    private String interviewDate;

    public Application(int id, String company, String role,
                       String applicationDate, String status,
                       String interviewDate) {
        this.id = id;
        this.company = company;
        this.role = role;
        this.applicationDate = applicationDate;
        this.status = status;
        this.interviewDate = interviewDate;
    }

    public int getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Company: " + company
                + " | Role: " + role
                + " | Applied: " + applicationDate
                + " | Status: " + status
                + " | Interview: " + interviewDate;
    }
}