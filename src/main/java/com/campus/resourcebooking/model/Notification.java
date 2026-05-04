package com.campus.resourcebooking.model;

import com.campus.resourcebooking.enums.NotificationType;
import com.campus.resourcebooking.enums.NotificationChannel;
import com.campus.resourcebooking.enums.NotificationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Notification class representing system notifications to users
 */
public class Notification {
    private String notificationId;
    private String userId;
    private NotificationType type;
    private String subject;
    private String message;
    private NotificationChannel channel;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime deliveredAt;
    private String relatedBookingId;
    private LocalDateTime createdAt;

    // Constructor for new notification
    public Notification(String userId, NotificationType type, String message, NotificationChannel channel) {
        this.notificationId = UUID.randomUUID().toString();
        this.userId = userId;
        this.type = type;
        this.subject = generateSubject(type);
        this.message = message;
        this.channel = channel;
        this.status = NotificationStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
    }

    // Full constructor
    public Notification(String notificationId, String userId, NotificationType type, String subject,
                       String message, NotificationChannel channel, NotificationStatus status,
                       LocalDateTime sentAt, LocalDateTime deliveredAt, String relatedBookingId,
                       LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.type = type;
        this.subject = subject;
        this.message = message;
        this.channel = channel;
        this.status = status;
        this.sentAt = sentAt;
        this.deliveredAt = deliveredAt;
        this.relatedBookingId = relatedBookingId;
        this.createdAt = createdAt;
    }

    // Static factory method
    public static Notification createNotification(String userId, NotificationType type,
                                                String message, NotificationChannel channel) {
        return new Notification(userId, type, message, channel);
    }

    public void sendNotification() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    public void retryFailedNotification() {
        if (this.status == NotificationStatus.FAILED) {
            this.status = NotificationStatus.DRAFT;
        }
    }

    public void markAsDelivered() {
        this.status = NotificationStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
    }

    public List<Notification> getNotificationHistory(String userId) {
        // Implementation would query database
        return List.of();
    }

    public void cancelNotification() {
        this.status = NotificationStatus.FAILED;
    }

    public boolean isDelivered() {
        return status == NotificationStatus.DELIVERED;
    }

    public boolean isFailed() {
        return status == NotificationStatus.FAILED || status == NotificationStatus.BOUNCED;
    }

    private String generateSubject(NotificationType type) {
        return switch (type) {
            case CONFIRMATION -> "Booking Confirmation";
            case APPROVAL -> "Booking Approved";
            case REJECTION -> "Booking Rejected";
            case REMINDER -> "Booking Reminder";
            case CANCELLATION -> "Booking Cancelled";
            case NO_SHOW_WARNING -> "No Show Warning";
        };
    }

    // Getters and setters
    public String getNotificationId() { return notificationId; }
    public String getUserId() { return userId; }
    public NotificationType getType() { return type; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public NotificationChannel getChannel() { return channel; }
    public NotificationStatus getStatus() { return status; }
    public LocalDateTime getSentAt() { return sentAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public String getRelatedBookingId() { return relatedBookingId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setRelatedBookingId(String relatedBookingId) { this.relatedBookingId = relatedBookingId; }
}