package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.model.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends Repository<Notification, String> {
    List<Notification> findByUserId(String userId);
    Optional<Notification> findByNotificationId(String notificationId);
}
