package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.Database.*;
import com.DriverMileageTracker.Backend.Dto.MonthlyReportDTO;
//import com.DriverMileageTracker.Backend.Mappers.MonthlyReportMapper;
import com.DriverMileageTracker.Backend.Enum.ReportStatus;
import com.DriverMileageTracker.Backend.Exception.ResourceNotFoundException;
import com.DriverMileageTracker.Backend.Mappers.MileageRecordMapper;
import com.DriverMileageTracker.Backend.Mappers.MonthlyReportMapper;
import com.DriverMileageTracker.Backend.Repository.MileageRecordRepository;
import com.DriverMileageTracker.Backend.Repository.MonthlyReportRepository;
import com.DriverMileageTracker.Backend.Repository.NotificationRepository;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import com.DriverMileageTracker.Backend.Services.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {
    @Autowired
    private MonthlyReportRepository monthlyReportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MileageRecordRepository recordRepository;
    @Autowired
    private MonthlyReportMapper reportMapper;

    @Autowired
    private MileageRecordMapper recordMapper;

    @Autowired
    private NotificationRepository notificationRepo;

    public List<MonthlyReportDTO> getAllReports() {
        return monthlyReportRepository.findAll().stream().map(reportMapper::toDTO).toList();
    }

    public MonthlyReportDTO getReportById(Long id) {
        return null;
//        return reportMapper.toDTO(reportRepository.findById(id).orElse(null));
    }

    @Override
    public MonthlyReportDTO createReport(Long userId, int month, int year) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<MileageRecord> records = recordRepository.findByUserAndMonthYear(user, month, year);
        // If > 25 daily records found, save a MonthlyReport with status PENDING
        if (!records.isEmpty()) {
            String formattedMonth = String.format("%04d-%02d", year, month);
            boolean exists = monthlyReportRepository.existsByUserAndMonth(user, formattedMonth);
            if (!exists) {
                MonthlyReport report = new MonthlyReport();
                report.setUser(user);
                report.setMonth(formattedMonth);
                report.setTotalKm(records.stream().mapToInt(MileageRecord::getTotalKm).sum());
                report.setStatus(ReportStatus.PENDING);
                report.setCreatedBy(user.getId()); // or use current logged-in ID
                report.setCreatedDatetime(LocalDateTime.now());
                MonthlyReport savedReport = monthlyReportRepository.save(report);
                return reportMapper.toDTO(savedReport);
            }else {
                return reportMapper.toDTO(monthlyReportRepository.findByUserIdAndMonth(userId,formattedMonth));
            }
        }
        throw new IllegalStateException("Not enough records to generate report (must be more than 25 days).");
    }

    @Override
    public MonthlyReportDTO updateReport(Long id, MonthlyReportDTO reportDTO) {
        return null;
    }

    @Override
    public void deleteReport(Long id) {

    }

    public List<MonthlyReportDTO> getPendingReports() {
        List<MonthlyReport> reports = monthlyReportRepository.findByStatus(ReportStatus.PENDING);
        return reports.stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyReportDTO> getReportsByUserId(Long userId) {
        try {
            List<MonthlyReport> reports = monthlyReportRepository.findByUserId(userId);

            return reports.stream()
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // Log the error for debugging (optional but recommended)
            System.err.println("Error fetching monthly reports for userId " + userId + ": " + e.getMessage());
            e.printStackTrace(); // for full stack trace

            // Return empty list or handle as needed
            return Collections.emptyList();
        }
    }



    public MonthlyReportDTO updateStatus(Long reportId, ReportStatus newStatus) {
        MonthlyReport report = monthlyReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));

        // Update report status
        report.setStatus(newStatus);
        report.setLastUpdatedDatetime(LocalDateTime.now());
        MonthlyReport updated = monthlyReportRepository.save(report);

        // ✅ Send notification to the driver
        Users driver = report.getUser(); // Assuming MonthlyReport has `Users user`

        String title = "Report Status Update";
        String message = "";

        switch (newStatus) {
            case GENERATED -> message = "Your monthly report has been approved and generated.";
            case REJECTED  -> message = "Your monthly report has been rejected. Please review and correct.";
            case PENDING   -> message = "Your monthly report is currently pending for admin approval.";
        }

        // Create Notification
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType("RESPONSE");
        notification.setStatus("UNREAD");

        Users admin = userRepository.findAll().stream()
                .filter(user -> user.getRoles()
                        .stream()
                        .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        notification.setSender(admin);
        notification.setReceiver(driver); // To the driver who submitted report
        notification.setRelatedReport(report);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepo.save(notification);

        return reportMapper.toDTO(updated);
    }


}
