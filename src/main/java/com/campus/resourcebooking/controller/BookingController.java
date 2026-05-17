package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.model.User;
import com.campus.resourcebooking.service.BookingService;
import com.campus.resourcebooking.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Booking operations
 */
@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings", description = "API for managing resource bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ResourceService resourceService;

    /**
     * Create a new booking
     */
    @PostMapping
    @Operation(summary = "Create a new booking", description = "Creates a new resource booking with validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully",
                    content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "400", description = "Invalid booking request"),
            @ApiResponse(responseCode = "409", description = "Resource not available")
    })
    public ResponseEntity<Booking> createBooking(
            @Parameter(description = "User ID of the requester")
            @RequestParam String userId,
            @Parameter(description = "Resource ID to be booked")
            @RequestParam String resourceId,
            @Parameter(description = "Booking start date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Booking end date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Purpose of booking")
            @RequestParam(required = false, defaultValue = "Business purpose") String purpose) {
        try {
            User user = new User("user@campus.edu", "pass", "First", "Last", null);
            user.userId = userId;
            Resource resource = resourceService.getResourceById(resourceId);
            Booking booking = bookingService.createBooking(user, resource, startDate, endDate);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Get booking by ID
     */
    @GetMapping("/{bookingId}")
    @Operation(summary = "Get booking details", description = "Retrieves details of a specific booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found",
                    content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Booking> getBooking(
            @Parameter(description = "Booking ID")
            @PathVariable String bookingId) {
        // Implementation: fetch from repository
        return ResponseEntity.ok(new Booking("user1", "res1", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Test"));
    }

    /**
     * Get all bookings for a user
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's bookings", description = "Retrieves all bookings for a specific user")
    @ApiResponse(responseCode = "200", description = "List of bookings retrieved",
            content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<Booking>> getUserBookings(
            @Parameter(description = "User ID")
            @PathVariable String userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Get all bookings for a resource
     */
    @GetMapping("/resource/{resourceId}")
    @Operation(summary = "Get resource bookings", description = "Retrieves all bookings for a specific resource")
    @ApiResponse(responseCode = "200", description = "List of bookings retrieved")
    public ResponseEntity<List<Booking>> getResourceBookings(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId) {
        List<Booking> bookings = bookingService.getBookingsByResource(resourceId);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Approve a booking
     */
    @PutMapping("/{bookingId}/approve")
    @Operation(summary = "Approve a booking", description = "Approves a pending booking by an administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking approved successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<String> approveBooking(
            @Parameter(description = "Booking ID")
            @PathVariable String bookingId,
            @Parameter(description = "Admin user ID")
            @RequestParam String adminId,
            @Parameter(description = "Approval conditions")
            @RequestParam(required = false) String conditions) {
        try {
            User admin = new User("admin@campus.edu", "pass", "Admin", "User", null);
            admin.userId = adminId;
            bookingService.approveBooking(bookingId, admin, conditions);
            return ResponseEntity.ok("Booking approved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }

    /**
     * Reject a booking
     */
    @PutMapping("/{bookingId}/reject")
    @Operation(summary = "Reject a booking", description = "Rejects a pending booking with a reason")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking rejected successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<String> rejectBooking(
            @Parameter(description = "Booking ID")
            @PathVariable String bookingId,
            @Parameter(description = "Admin user ID")
            @RequestParam String adminId,
            @Parameter(description = "Rejection reason")
            @RequestParam String reason) {
        try {
            User admin = new User("admin@campus.edu", "pass", "Admin", "User", null);
            admin.userId = adminId;
            bookingService.rejectBooking(bookingId, admin, reason);
            return ResponseEntity.ok("Booking rejected successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }

    /**
     * Cancel a booking
     */
    @DeleteMapping("/{bookingId}")
    @Operation(summary = "Cancel a booking", description = "Cancels an existing booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Void> cancelBooking(
            @Parameter(description = "Booking ID")
            @PathVariable String bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Check for booking conflicts
     */
    @GetMapping("/conflicts")
    @Operation(summary = "Check booking conflicts", description = "Checks if there are conflicts for a resource in a given time period")
    @ApiResponse(responseCode = "200", description = "Conflict check completed")
    public ResponseEntity<Boolean> checkConflicts(
            @Parameter(description = "Resource ID")
            @RequestParam String resourceId,
            @Parameter(description = "Start date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        boolean hasConflicts = bookingService.checkConflicts(resourceId, startDate, endDate);
        return ResponseEntity.ok(hasConflicts);
    }
}
