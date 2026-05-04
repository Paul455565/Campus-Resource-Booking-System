package com.campus.resourcebooking.model;

import com.campus.resourcebooking.enums.BookingStatus;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Booking class representing a resource reservation request
 */
public class Booking {
    private String bookingId;
    private String userId;
    private String resourceId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BookingStatus status;
    private String purpose;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor for new booking
    public Booking(String userId, String resourceId, LocalDateTime startDate,
                  LocalDateTime endDate, String purpose) {
        this.bookingId = UUID.randomUUID().toString();
        this.userId = userId;
        this.resourceId = resourceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.PENDING;
        this.purpose = purpose;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Full constructor
    public Booking(String bookingId, String userId, String resourceId, LocalDateTime startDate,
                  LocalDateTime endDate, BookingStatus status, String purpose, String notes,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.resourceId = resourceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.purpose = purpose;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Static factory method
    public static Booking submitBooking(String resourceId, LocalDateTime startDate,
                                       LocalDateTime endDate, String purpose) {
        // In real implementation, userId would come from authenticated user
        String userId = "current_user_id";
        return new Booking(userId, resourceId, startDate, endDate, purpose);
    }

    public void cancelBooking() {
        if (this.status == BookingStatus.PENDING || this.status == BookingStatus.APPROVED) {
            this.status = BookingStatus.CANCELLED;
            this.updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalStateException("Cannot cancel booking in current status");
        }
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void updateStatus(BookingStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void sendConfirmation() {
        // Implementation would send notification
    }

    public long calculateDuration() {
        return ChronoUnit.MINUTES.between(startDate, endDate);
    }

    public boolean checkConflict() {
        // Simplified conflict check - in real implementation would query database
        return false;
    }

    public void markAsNoShow() {
        this.status = BookingStatus.NO_SHOW;
        this.updatedAt = LocalDateTime.now();
    }

    public Approval getApproval() {
        // Implementation would query database
        return null;
    }

    public boolean isApproved() {
        return status == BookingStatus.APPROVED;
    }

    public boolean isPending() {
        return status == BookingStatus.PENDING;
    }

    // Getters and setters
    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getResourceId() { return resourceId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getPurpose() { return purpose; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setNotes(String notes) { this.notes = notes; }
}