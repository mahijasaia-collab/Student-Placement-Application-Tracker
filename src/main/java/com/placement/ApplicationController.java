package com.placement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private static final List<String> STATUSES =
            Arrays.asList("Applied", "Interview", "Selected", "Rejected");

    private final PlacementManager manager;

    public ApplicationController(PlacementManager manager) {
        this.manager = manager;
    }

    public record NewApplication(String company, String role, String applicationDate) {}
    public record StatusUpdate(String status) {}
    public record InterviewUpdate(String interviewDate) {}

    // PlacementManager is not thread-safe, so every method is synchronized
    @GetMapping
    public synchronized List<Application> list(
            @RequestParam(required = false) String company) {
        if (company == null || company.isBlank()) {
            return manager.getAllApplications();
        }
        return manager.searchByCompany(company);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized Application add(@RequestBody NewApplication req) {
        requireText(req.company(), "Company");
        requireText(req.role(), "Role");
        requireDate(req.applicationDate());
        return manager.addApplication(
                req.company().trim(), req.role().trim(), req.applicationDate());
    }

    @PutMapping("/{id}/status")
    public synchronized Application updateStatus(@PathVariable int id,
                                                 @RequestBody StatusUpdate req) {
        if (req.status() == null || !STATUSES.contains(req.status())) {
            throw bad("Status must be one of " + STATUSES);
        }
        if (!manager.updateStatus(id, req.status())) {
            throw notFound(id);
        }
        return manager.findById(id);
    }

    @PutMapping("/{id}/interview")
    public synchronized Application updateInterview(@PathVariable int id,
                                                    @RequestBody InterviewUpdate req) {
        requireDate(req.interviewDate());
        if (!manager.updateInterviewDate(id, req.interviewDate())) {
            throw notFound(id);
        }
        return manager.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public synchronized void delete(@PathVariable int id) {
        if (!manager.deleteApplication(id)) {
            throw notFound(id);
        }
    }

    // ---------- helpers ----------

    private void requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw bad(name + " is required");
        }
    }

    private void requireDate(String value) {
        if (value == null) {
            throw bad("Date is required (YYYY-MM-DD)");
        }
        try {
            LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw bad("Date must be in YYYY-MM-DD format");
        }
    }

    private ResponseStatusException bad(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    private ResponseStatusException notFound(int id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Application " + id + " not found");
    }
}