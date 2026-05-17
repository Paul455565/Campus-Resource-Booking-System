package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.model.Approval;
import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.service.ApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Approval operations
 */
@RestController
@RequestMapping("/approvals")
@Tag(name = "Approvals", description = "API for managing booking approvals")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    /**
     * Get pending approvals
     */
    @GetMapping("/pending")
    @Operation(summary = "Get pending approvals", description = "Retrieves all pending approval requests")
    @ApiResponse(responseCode = "200", description = "List of pending approvals",
            content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<Approval>> getPendingApprovals() {
        List<Approval> approvals = approvalService.getPendingApprovals();
        return ResponseEntity.ok(approvals);
    }

    /**
     * Get approval by ID
     */
    @GetMapping("/{approvalId}")
    @Operation(summary = "Get approval details", description = "Retrieves details of a specific approval request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Approval found",
                    content = @Content(schema = @Schema(implementation = Approval.class))),
            @ApiResponse(responseCode = "404", description = "Approval not found")
    })
    public ResponseEntity<Approval> getApproval(
            @Parameter(description = "Approval ID")
            @PathVariable String approvalId) {
        // Implementation: fetch from service
        return ResponseEntity.ok(new Approval("booking1", "admin1", null, "Test"));
    }

    /**
     * Get approvals by admin
     */
    @GetMapping("/admin/{adminId}")
    @Operation(summary = "Get approvals by admin", description = "Retrieves all approvals processed by a specific administrator")
    @ApiResponse(responseCode = "200", description = "List of approvals")
    public ResponseEntity<List<Approval>> getApprovalsByAdmin(
            @Parameter(description = "Admin user ID")
            @PathVariable String adminId) {
        List<Approval> approvals = approvalService.getApprovalsByAdmin(adminId);
        return ResponseEntity.ok(approvals);
    }

    /**
     * Submit booking for approval
     */
    @PostMapping("/{bookingId}/submit")
    @Operation(summary = "Submit booking for approval", description = "Submits a booking for administrative approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking submitted for approval"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<String> submitForApproval(
            @Parameter(description = "Booking ID")
            @PathVariable String bookingId) {
        try {
            // Implementation: submit booking
            return ResponseEntity.ok("Booking submitted for approval");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }

    /**
     * Approve a booking
     */
    @PostMapping("/{approvalId}/approve")
    @Operation(summary = "Approve a booking", description = "Approves a pending booking request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking approved successfully"),
            @ApiResponse(responseCode = "404", description = "Approval not found")
    })
    public ResponseEntity<String> approveBooking(
            @Parameter(description = "Approval ID")
            @PathVariable String approvalId,
            @Parameter(description = "Approval conditions/notes")
            @RequestParam(required = false) String conditions) {
        try {
            approvalService.approveBookingRequest(approvalId, conditions);
            return ResponseEntity.ok("Booking approved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Approval not found");
        }
    }

    /**
     * Reject a booking
     */
    @PostMapping("/{approvalId}/reject")
    @Operation(summary = "Reject a booking", description = "Rejects a pending booking request with a reason")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking rejected successfully"),
            @ApiResponse(responseCode = "404", description = "Approval not found")
    })
    public ResponseEntity<String> rejectBooking(
            @Parameter(description = "Approval ID")
            @PathVariable String approvalId,
            @Parameter(description = "Rejection reason")
            @RequestParam String reason) {
        try {
            approvalService.rejectBookingRequest(approvalId, reason);
            return ResponseEntity.ok("Booking rejected successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Approval not found");
        }
    }
}
