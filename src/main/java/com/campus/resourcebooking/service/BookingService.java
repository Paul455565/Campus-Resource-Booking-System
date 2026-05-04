package com.campus.resourcebooking.service;

import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.model.Approval;
import com.campus.resourcebooking.model.User;
import com.campus.resourcebooking.enums.ApprovalDecision;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing booking operations
 */
public class BookingService {
    private final Object bookingRepository; // Would be a proper repository interface
    private final ResourceService resourceService;
    private final NotificationService notificationService;

    public BookingService(ResourceService resourceService, NotificationService notificationService) {
        this.bookingRepository = null; // Mock repository
        this.resourceService = resourceService;
        this.notificationService = notificationService;
    }

    public Booking createBooking(User user, Resource resource, LocalDateTime startDate,
                                LocalDateTime endDate) {
        // Validate booking constraints
        if (!resourceService.checkResourceAvailability(resource.getResourceId(), startDate, endDate)) {
            throw new IllegalArgumentException("Resource not available for the requested time");
        }

        Booking booking = new Booking(user.getUserId(), resource.getResourceId(),
                                    startDate, endDate, "Business purpose");
        return booking;
    }

    public void cancelBooking(String bookingId) {
        // In real implementation, retrieve booking from repository
        Booking booking = new Booking("user1", "resource1", LocalDateTime.now(),
                                    LocalDateTime.now().plusHours(1), "Test");
        booking.cancelBooking();
    }

    public List<Booking> getBookingsByUser(String userId) {
        // Implementation would query repository
        return List.of();
    }

    public List<Booking> getBookingsByResource(String resourceId) {
        // Implementation would query repository
        return List.of();
    }

    public boolean checkConflicts(String resourceId, LocalDateTime startDate, LocalDateTime endDate) {
        // Implementation would check for overlapping bookings
        return false;
    }

    public void approveBooking(String bookingId, User admin, String conditions) {
        Approval approval = new Approval(bookingId, admin.getUserId(),
                                       ApprovalDecision.APPROVED, "Approved by admin");
        approval.approveBooking(bookingId, conditions);
        // Update booking status
    }

    public void rejectBooking(String bookingId, User admin, String reason) {
        Approval approval = new Approval(bookingId, admin.getUserId(),
                                       ApprovalDecision.REJECTED, reason);
        approval.rejectBooking(bookingId, reason);
        // Update booking status
    }
}