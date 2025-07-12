package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.Database.Notification;
import com.DriverMileageTracker.Backend.Dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO createNotification(NotificationDTO dto);
    void markAsRead(Long notificationId);

    List<NotificationDTO> getUserNotifications(Long userId);
}
