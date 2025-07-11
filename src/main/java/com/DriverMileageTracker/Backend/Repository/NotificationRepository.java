package com.DriverMileageTracker.Backend.Repository;

import com.DriverMileageTracker.Backend.Database.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);
}
