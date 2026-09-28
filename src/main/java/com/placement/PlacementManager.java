package com.placement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlacementManager {
    private final List<Application> applications = new ArrayList<>();
    private int nextId = 1;

    private final String fileName;

    public PlacementManager() {
        this("applications.dat");
    }

    public PlacementManager(String fileName) {
        this.fileName = fileName;
        loadApplications();
    }

    public Application addApplication(String company, String role,
                                       String applicationDate) {
        Application application = new Application(
                nextId++, company, role,
                applicationDate, "Applied", "Not scheduled"
        );

        applications.add(application);
        saveApplications();
        return application;
    }

    public List<Application> getAllApplications() {
        return new ArrayList<>(applications);
    }

    public List<Application> searchByCompany(String company) {
        List<Application> results = new ArrayList<>();

        for (Application application : applications) {
            if (application.getCompany()
                    .toLowerCase()
                    .contains(company.toLowerCase())) {
                results.add(application);
            }
        }

        return results;
    }

    public boolean updateStatus(int id, String status) {
        Application application = findById(id);

        if (application == null) {
            return false;
        }

        application.setStatus(status);
        saveApplications();
        return true;
    }

    public boolean updateInterviewDate(int id, String date) {
        Application application = findById(id);

        if (application == null) {
            return false;
        }

        application.setInterviewDate(date);
        saveApplications();
        return true;
    }

    public boolean deleteApplication(int id) {
        Application application = findById(id);

        if (application == null) {
            return false;
        }

        applications.remove(application);
        saveApplications();
        return true;
    }

    public Application findById(int id) {
        for (Application application : applications) {
            if (application.getId() == id) {
                return application;
            }
        }

        return null;
    }

    private void saveApplications() {
        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            output.writeObject(new ArrayList<>(applications));
            output.writeInt(nextId);
        } catch (IOException e) {
            System.out.println("Could not save records: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadApplications() {
        File file = new File(fileName);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(file))) {
            applications.addAll((List<Application>) input.readObject());
            nextId = input.readInt();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load records: " + e.getMessage());
        }
    }
}