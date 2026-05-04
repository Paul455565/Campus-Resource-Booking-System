package com.campus.resourcebooking.model;

import com.campus.resourcebooking.enums.MaintenanceStatus;
import com.campus.resourcebooking.enums.MaintenanceSeverity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Maintenance class representing scheduled maintenance tasks for resources
 */
public class Maintenance {
    private String maintenanceId;
    private String resourceId;
    private String scheduledBy;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;
    private MaintenanceSeverity severity;
    private MaintenanceStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    // Constructor for new maintenance
    public Maintenance(String resourceId, String scheduledBy, LocalDateTime startDate,
                      LocalDateTime endDate, String reason, MaintenanceSeverity severity) {
        this.maintenanceId = UUID.randomUUID().toString();
        this.resourceId = resourceId;
        this.scheduledBy = scheduledBy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.severity = severity;
        this.status = MaintenanceStatus.SCHEDULED;
        this.createdAt = LocalDateTime.now();
    }

    // Full constructor
    public Maintenance(String maintenanceId, String resourceId, String scheduledBy,
                      LocalDateTime startDate, LocalDateTime endDate, String reason,
                      MaintenanceSeverity severity, MaintenanceStatus status, String notes,
                      LocalDateTime createdAt, LocalDateTime completedAt) {
        this.maintenanceId = maintenanceId;
        this.resourceId = resourceId;
        this.scheduledBy = scheduledBy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.severity = severity;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    // Static factory method
    public static Maintenance scheduleMaintenance(String resourceId, LocalDateTime startDate,
                                                LocalDateTime endDate, String reason) {
        String scheduledBy = "current_user_id"; // Would come from authenticated user
        return new Maintenance(resourceId, scheduledBy, startDate, endDate, reason, MaintenanceSeverity.MEDIUM);
    }

    public void startMaintenance() {
        this.status = MaintenanceStatus.IN_PROGRESS;
    }

    public void completeMaintenance() {
        this.status = MaintenanceStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void cancelMaintenance() {
        this.status = MaintenanceStatus.CANCELLED;
    }

    public List<Booking> getAffectedBookings() {
        // Implementation would query database for bookings during maintenance period
        return List.of();
    }

    public void notifyAffectedUsers() {
        // Implementation would send notifications
    }

    public void rescheduleBookings() {
        // Implementation would reschedule affected bookings
    }

    public boolean isActive() {
        return status == MaintenanceStatus.IN_PROGRESS;
    }

    public boolean hasOverlappingBookings() {
        // Implementation would check for overlapping bookings
        return false;
    }

    // Getters and setters
    public String getMaintenanceId() { return maintenanceId; }
    public String getResourceId() { return resourceId; }
    public String getScheduledBy() { return scheduledBy; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getReason() { return reason; }
    public MaintenanceSeverity getSeverity() { return severity; }
    public MaintenanceStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }

    public void setNotes(String notes) { this.notes = notes; }
    public void setSeverity(MaintenanceSeverity severity) { this.severity = severity; }
}