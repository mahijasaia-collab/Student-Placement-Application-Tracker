package com.placement;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PlacementManager manager = new PlacementManager();

    public static void main(String[] args) {
        while (true) {
            showMenu();

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addApplication();
                case "2" -> viewApplications();
                case "3" -> searchApplications();
                case "4" -> updateStatus();
                case "5" -> updateInterviewDate();
                case "6" -> deleteApplication();
                case "7" -> {
                    System.out.println("Thank you for using the tracker!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n==================================");
        System.out.println(" STUDENT PLACEMENT APPLICATION TRACKER");
        System.out.println("==================================");
        System.out.println("1. Add Application");
        System.out.println("2. View All Applications");
        System.out.println("3. Search by Company");
        System.out.println("4. Update Application Status");
        System.out.println("5. Update Interview Date");
        System.out.println("6. Delete Application");
        System.out.println("7. Exit");
        System.out.println("==================================");
    }

    private static void addApplication() {
        System.out.print("Enter company name: ");
        String company = scanner.nextLine().trim();

        System.out.print("Enter job role: ");
        String role = scanner.nextLine().trim();

        System.out.print("Enter application date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        if (company.isEmpty() || role.isEmpty() || date.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        Application application =
                manager.addApplication(company, role, date);

        System.out.println("Application added successfully!");
        System.out.println("Generated ID: " + application.getId());
    }

    private static void viewApplications() {
        List<Application> applications = manager.getAllApplications();

        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        System.out.println("\n----- YOUR APPLICATIONS -----");
        for (Application application : applications) {
            System.out.println(application);
        }
    }

    private static void searchApplications() {
        System.out.print("Enter company name to search: ");
        String company = scanner.nextLine().trim();

        List<Application> results = manager.searchByCompany(company);

        if (results.isEmpty()) {
            System.out.println("No matching applications found.");
            return;
        }

        for (Application application : results) {
            System.out.println(application);
        }
    }

    private static void updateStatus() {
        System.out.print("Enter application ID: ");
        int id = readId();

        if (id == -1) {
            return;
        }

        System.out.println("Choose status:");
        System.out.println("1. Applied");
        System.out.println("2. Assessment");
        System.out.println("3. Interview");
        System.out.println("4. Selected");
        System.out.println("5. Rejected");

        System.out.print("Enter status choice: ");
        String choice = scanner.nextLine().trim();

        String status = switch (choice) {
            case "1" -> "Applied";
            case "2" -> "Assessment";
            case "3" -> "Interview";
            case "4" -> "Selected";
            case "5" -> "Rejected";
            default -> null;
        };

        if (status == null) {
            System.out.println("Invalid status choice.");
            return;
        }

        if (manager.updateStatus(id, status)) {
            System.out.println("Status updated successfully!");
        } else {
            System.out.println("Application ID not found.");
        }
    }

    private static void updateInterviewDate() {
        System.out.print("Enter application ID: ");
        int id = readId();

        if (id == -1) {
            return;
        }

        System.out.print("Enter interview date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        if (date.isEmpty()) {
            System.out.println("Date cannot be empty.");
            return;
        }

        if (manager.updateInterviewDate(id, date)) {
            System.out.println("Interview date updated successfully!");
        } else {
            System.out.println("Application ID not found.");
        }
    }

    private static void deleteApplication() {
        System.out.print("Enter application ID to delete: ");
        int id = readId();

        if (id == -1) {
            return;
        }

        if (manager.deleteApplication(id)) {
            System.out.println("Application deleted successfully!");
        } else {
            System.out.println("Application ID not found.");
        }
    }

    private static int readId() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric ID.");
            return -1;
        }
    }
}