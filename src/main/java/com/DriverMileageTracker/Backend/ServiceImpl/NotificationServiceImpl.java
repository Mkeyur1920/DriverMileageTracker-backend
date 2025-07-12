package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.Database.MonthlyReport;
import com.DriverMileageTracker.Backend.Database.Notification;
import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Dto.NotificationDTO;
import com.DriverMileageTracker.Backend.Mappers.NotificationMapper;
import com.DriverMileageTracker.Backend.Repository.MonthlyReportRepository;
import com.DriverMileageTracker.Backend.Repository.NotificationRepository;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import com.DriverMileageTracker.Backend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MonthlyReportRepository reportRepo;

    @Autowired
    private NotificationMapper notificationMapper;
    @Override
    public NotificationDTO createNotification(NotificationDTO dto) {
        Users sender = userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Users receiver = userRepo.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        MonthlyReport report = null;
        if (dto.getRelatedReportId() != null) {
            report = reportRepo.findById(dto.getRelatedReportId())
                    .orElseThrow(() -> new RuntimeException("Report not found"));
        }

        Notification notification = notificationMapper. toEntity(dto, sender, receiver, report);
        Notification saved = notificationRepo.save(notification);
        return notificationMapper.toDto(saved);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification n = notificationRepo.findById(notificationId).orElseThrow();
        n.setStatus("READ");
            notificationRepo.save(n);
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        List<Notification> notifications = notificationRepo
                .findByReceiverIdOrderByCreatedAtDesc(userId);

        return notifications.stream()
                .map(notificationMapper::toDto)
                .toList();
    }
}
