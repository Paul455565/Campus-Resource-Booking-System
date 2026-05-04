package com.campus.resourcebooking.service;

import com.campus.resourcebooking.model.Notification;
import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.model.Approval;
import com.campus.resourcebooking.enums.NotificationType;
import com.campus.resourcebooking.enums.NotificationChannel;
import com.campus.resourcebooking.enums.NotificationStatus;
import java.util.List;

/**
 * Service class for managing notifications
 */
public class NotificationService {
    private final Object notificationRepository; // Would be a proper repository interface
    private final Object emailProvider; // Would be an email service interface

    public NotificationService() {
        this.notificationRepository = null;
        this.emailProvider = null;
    }

    public void sendBookingConfirmation(Booking booking) {
        Notification notification = Notification.createNotification(
            booking.getUserId(),
            NotificationType.CONFIRMATION,
            "Your booking has been confirmed for " + booking.getStartDate(),
            NotificationChannel.EMAIL
        );
        notification.sendNotification();
    }

    public void sendApprovalNotification(Booking booking, Approval approval) {
        NotificationType type = approval.getDecision() == ApprovalDecision.APPROVED ?
                               NotificationType.APPROVAL : NotificationType.REJECTION;

        Notification notification = Notification.createNotification(
            booking.getUserId(),
            type,
            "Your booking has been " + approval.getDecision().toString().toLowerCase(),
            NotificationChannel.EMAIL
        );
        notification.sendNotification();
    }

    public void sendRejectionNotification(Booking booking, String reason) {
        Notification notification = Notification.createNotification(
            booking.getUserId(),
            NotificationType.REJECTION,
            "Your booking was rejected: " + reason,
            NotificationChannel.EMAIL
        );
        notification.sendNotification();
    }

    public void sendReminderNotification(Booking booking) {
        Notification notification = Notification.createNotification(
            booking.getUserId(),
            NotificationType.REMINDER,
            "Reminder: You have a booking tomorrow at " + booking.getStartDate(),
            NotificationChannel.EMAIL
        );
        notification.sendNotification();
    }

    public void retryFailedNotifications() {
        // Implementation would find failed notifications and retry
    }

    public NotificationStatus getDeliveryStatus(String notificationId) {
        // Implementation would query repository
        return NotificationStatus.SENT;
    }
}