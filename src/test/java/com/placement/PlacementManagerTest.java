package com.placement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PlacementManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldAddApplication() {
        PlacementManager manager =
                new PlacementManager(tempDir.resolve("test.dat").toString());

        Application application =
                manager.addApplication("Infosys", "System Engineer", "2026-09-28");

        assertEquals(1, application.getId());
        assertEquals("Infosys", application.getCompany());
        assertEquals("Applied", application.getStatus());
    }

    @Test
    void shouldSearchByCompany() {
        PlacementManager manager =
                new PlacementManager(tempDir.resolve("test.dat").toString());

        manager.addApplication("Infosys", "System Engineer", "2026-09-28");
        manager.addApplication("TCS", "Assistant System Engineer", "2026-09-28");

        assertEquals(1, manager.searchByCompany("info").size());
    }

    @Test
    void shouldUpdateStatus() {
        PlacementManager manager =
                new PlacementManager(tempDir.resolve("test.dat").toString());

        Application application =
                manager.addApplication("Infosys", "Developer", "2026-09-28");

        assertTrue(manager.updateStatus(application.getId(), "Interview"));
        assertEquals("Interview", application.getStatus());
    }

    @Test
    void shouldDeleteApplication() {
        PlacementManager manager =
                new PlacementManager(tempDir.resolve("test.dat").toString());

        Application application =
                manager.addApplication("TCS", "Developer", "2026-09-28");

        assertTrue(manager.deleteApplication(application.getId()));
        assertTrue(manager.getAllApplications().isEmpty());
    }

    @Test
    void shouldReturnFalseForUnknownApplication() {
        PlacementManager manager =
                new PlacementManager(tempDir.resolve("test.dat").toString());

        assertFalse(manager.updateStatus(999, "Selected"));
        assertFalse(manager.deleteApplication(999));
    }
}