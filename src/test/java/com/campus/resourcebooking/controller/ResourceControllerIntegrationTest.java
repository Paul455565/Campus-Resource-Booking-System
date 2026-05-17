package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.service.ResourceService;
import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.enums.ResourceAvailability;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Resource Controller Integration Tests")
class ResourceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceService resourceService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Resource testResource;

    @BeforeEach
    void setUp() {
        testResource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
    }

    @Test
    @DisplayName("GET /api/resources should return all resources")
    void testGetAllResources() throws Exception {
        // Arrange
        List<Resource> resources = List.of(testResource);

        // Act & Assert
        mockMvc.perform(get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /api/resources/{resourceId} should return resource details")
    void testGetResource() throws Exception {
        // Arrange
        when(resourceService.getResourceById(anyString())).thenReturn(testResource);

        // Act & Assert
        mockMvc.perform(get("/api/resources/res1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/resources should create resource")
    void testCreateResource() throws Exception {
        // Arrange
        when(resourceService.getResourceById(anyString())).thenReturn(testResource);

        // Act & Assert
        mockMvc.perform(post("/api/resources")
                .param("name", "Lab A")
                .param("description", "Computer Lab")
                .param("type", "LABORATORY")
                .param("location", "Building A")
                .param("capacity", "30")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /api/resources/{resourceId} should update resource")
    void testUpdateResource() throws Exception {
        // Arrange
        when(resourceService.getResourceById(anyString())).thenReturn(testResource);

        // Act & Assert
        mockMvc.perform(put("/api/resources/res1")
                .param("name", "Updated Lab A")
                .param("description", "Updated Computer Lab")
                .param("capacity", "40")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/resources/{resourceId} should delete resource")
    void testDeleteResource() throws Exception {
        // Arrange
        when(resourceService.getResourceById(anyString())).thenReturn(testResource);

        // Act & Assert
        mockMvc.perform(delete("/api/resources/res1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/resources/{resourceId}/availability should check availability")
    void testCheckAvailability() throws Exception {
        // Arrange
        when(resourceService.checkResourceAvailability(anyString(), any(), any())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/resources/res1/availability")
                .param("startDate", "2025-01-01T10:00:00")
                .param("endDate", "2025-01-01T12:00:00"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/resources/{resourceId}/maintenance should schedule maintenance")
    void testScheduleMaintenance() throws Exception {
        // Arrange
        when(resourceService.getResourceById(anyString())).thenReturn(testResource);

        // Act & Assert
        mockMvc.perform(post("/api/resources/res1/maintenance")
                .param("startDate", "2025-01-15T10:00:00")
                .param("endDate", "2025-01-15T14:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/resources/{resourceId}/utilization should get utilization stats")
    void testGetUtilization() throws Exception {
        // Arrange
        when(resourceService.getResourceUtilization(anyString(), any())).thenReturn("75% utilization");

        // Act & Assert
        mockMvc.perform(get("/api/resources/res1/utilization")
                .param("period", "MONTHLY"))
                .andExpect(status().isOk());
    }
}
