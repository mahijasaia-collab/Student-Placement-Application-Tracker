package com.placement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplicationControllerTest {

    @TempDir
    Path tempDir;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        PlacementManager manager =
                new PlacementManager(tempDir.resolve("api-test.dat").toString());
        mvc = MockMvcBuilders.standaloneSetup(new ApplicationController(manager)).build();
    }

    @Test
    void shouldCreateAndListApplication() throws Exception {
        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"company\":\"Infosys\",\"role\":\"Developer\",\"applicationDate\":\"2026-11-02\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.company").value("Infosys"))
                .andExpect(jsonPath("$.status").value("Applied"));

        mvc.perform(get("/api/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldRejectInvalidDate() throws Exception {
        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"company\":\"TCS\",\"role\":\"Developer\",\"applicationDate\":\"02-11-2026\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404ForUnknownId() throws Exception {
        mvc.perform(put("/api/applications/999/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"Selected\"}"))
                .andExpect(status().isNotFound());
    }
}