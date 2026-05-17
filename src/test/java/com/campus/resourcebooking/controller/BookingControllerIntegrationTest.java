package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.model.User;
import com.campus.resourcebooking.service.BookingService;
import com.campus.resourcebooking.service.ResourceService;
import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.enums.UserRole;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Booking Controller Integration Tests")
class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private ResourceService resourceService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Booking testBooking;
    private Resource testResource;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("user@test.com", "pass", "John", "Doe", UserRole.STUDENT);
        testResource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        testBooking = new Booking(testUser.getUserId(), testResource.getResourceId(), 
            LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Test Booking");
    }

    @Test
    @DisplayName("GET /api/bookings/user/{userId} should return user's bookings")
    void testGetUserBookings() throws Exception {
        // Arrange
        List<Booking> bookings = List.of(testBooking);
        when(bookingService.getBookingsByUser(anyString())).thenReturn(bookings);

        // Act & Assert
        mockMvc.perform(get("/api/bookings/user/user1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /api/bookings/resource/{resourceId} should return resource bookings")
    void testGetResourceBookings() throws Exception {
        // Arrange
        List<Booking> bookings = List.of(testBooking);
        when(bookingService.getBookingsByResource(anyString())).thenReturn(bookings);

        // Act & Assert
        mockMvc.perform(get("/api/bookings/resource/res1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("DELETE /api/bookings/{bookingId} should cancel booking")
    void testCancelBooking() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/bookings/booking1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/bookings/conflicts should check booking conflicts")
    void testCheckConflicts() throws Exception {
        // Arrange
        when(bookingService.checkConflicts(anyString(), any(), any())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/api/bookings/conflicts")
                .param("resourceId", "res1")
                .param("startDate", "2025-01-01T10:00:00")
                .param("endDate", "2025-01-01T12:00:00"))
                .andExpect(status().isOk());
    }
}
