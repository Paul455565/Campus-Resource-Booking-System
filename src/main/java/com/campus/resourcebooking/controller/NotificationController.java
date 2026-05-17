package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.model.Notification;
import com.campus.resourcebooking.enums.NotificationStatus;
import com.campus.resourcebooking.service.NotificationService;
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
 * REST Controller for Notification operations
 */
@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "API for managing user notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Get notifications for a user
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user notifications", description = "Retrieves all notifications for a specific user")
    @ApiResponse(responseCode = "200", description = "List of notifications",
            content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<Notification>> getUserNotifications(
            @Parameter(description = "User ID")
            @PathVariable String userId,
            @Parameter(description = "Filter by status (optional)")
            @RequestParam(required = false) NotificationStatus status) {
        List<Notification> notifications = notificationService.getUserNotifications(userId, status);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notifications count
     */
    @GetMapping("/user/{userId}/unread-count")
    @Operation(summary = "Get unread notification count", description = "Returns the count of unread notifications for a user")
    @ApiResponse(responseCode = "200", description = "Unread notification count")
    public ResponseEntity<Integer> getUnreadCount(
            @Parameter(description = "User ID")
            @PathVariable String userId) {
        int count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * Get notification by ID
     */
    @GetMapping("/{notificationId}")
    @Operation(summary = "Get notification details", description = "Retrieves details of a specific notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification found",
                    content = @Content(schema = @Schema(implementation = Notification.class))),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<Notification> getNotification(
            @Parameter(description = "Notification ID")
            @PathVariable String notificationId) {
        try {
            Notification notification = notificationService.getNotificationById(notificationId);
            return ResponseEntity.ok(notification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Mark notification as read
     */
    @PutMapping("/{notificationId}/mark-read")
    @Operation(summary = "Mark notification as read", description = "Marks a notification as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<String> markAsRead(
            @Parameter(description = "Notification ID")
            @PathVariable String notificationId) {
        try {
            notificationService.markAsRead(notificationId);
            return ResponseEntity.ok("Notification marked as read");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        }
    }

    /**
     * Mark all notifications as read
     */
    @PutMapping("/user/{userId}/mark-all-read")
    @Operation(summary = "Mark all notifications as read", description = "Marks all notifications as read for a user")
    @ApiResponse(responseCode = "200", description = "All notifications marked as read")
    public ResponseEntity<String> markAllAsRead(
            @Parameter(description = "User ID")
            @PathVariable String userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok("All notifications marked as read");
    }

    /**
     * Delete notification
     */
    @DeleteMapping("/{notificationId}")
    @Operation(summary = "Delete notification", description = "Deletes a specific notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<Void> deleteNotification(
            @Parameter(description = "Notification ID")
            @PathVariable String notificationId) {
        try {
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Delete all notifications for a user
     */
    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete all user notifications", description = "Deletes all notifications for a specific user")
    @ApiResponse(responseCode = "204", description = "All notifications deleted successfully")
    public ResponseEntity<Void> deleteAllUserNotifications(
            @Parameter(description = "User ID")
            @PathVariable String userId) {
        notificationService.deleteAllUserNotifications(userId);
        return ResponseEntity.noContent().build();
    }
}
