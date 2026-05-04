package com.campus.resourcebooking.model;

import com.campus.resourcebooking.enums.ApprovalDecision;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Approval class representing admin decisions on booking requests
 */
public class Approval {
    private String approvalId;
    private String bookingId;
    private String adminId;
    private ApprovalDecision decision;
    private String reason;
    private String conditions;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;

    // Constructor for new approval
    public Approval(String bookingId, String adminId, ApprovalDecision decision, String reason) {
        this.approvalId = UUID.randomUUID().toString();
        this.bookingId = bookingId;
        this.adminId = adminId;
        this.decision = decision;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
        if (decision == ApprovalDecision.APPROVED) {
            this.approvedAt = LocalDateTime.now();
        }
    }

    // Full constructor
    public Approval(String approvalId, String bookingId, String adminId, ApprovalDecision decision,
                   String reason, String conditions, LocalDateTime approvedAt, LocalDateTime createdAt) {
        this.approvalId = approvalId;
        this.bookingId = bookingId;
        this.adminId = adminId;
        this.decision = decision;
        this.reason = reason;
        this.conditions = conditions;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
    }

    public void approveBooking(String bookingId, String conditions) {
        this.decision = ApprovalDecision.APPROVED;
        this.conditions = conditions;
        this.approvedAt = LocalDateTime.now();
    }

    public void rejectBooking(String bookingId, String reason) {
        this.decision = ApprovalDecision.REJECTED;
        this.reason = reason;
    }

    public ApprovalDecision getDecision() {
        return decision;
    }

    public void updateDecision(ApprovalDecision newDecision, String reason) {
        this.decision = newDecision;
        this.reason = reason;
        if (newDecision == ApprovalDecision.APPROVED) {
            this.approvedAt = LocalDateTime.now();
        }
    }

    public void notifyUser() {
        // Implementation would send notification
    }

    public boolean hasConditions() {
        return conditions != null && !conditions.trim().isEmpty();
    }

    public String getConditionsText() {
        return conditions;
    }

    // Getters and setters
    public String getApprovalId() { return approvalId; }
    public String getBookingId() { return bookingId; }
    public String getAdminId() { return adminId; }
    public String getReason() { return reason; }
    public String getConditions() { return conditions; }
    public LocalDateTime getApprovedAt() { return approvedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setConditions(String conditions) { this.conditions = conditions; }
}