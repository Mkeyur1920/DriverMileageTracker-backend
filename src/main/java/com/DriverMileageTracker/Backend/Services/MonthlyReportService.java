package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.Dto.MonthlyReportDTO;
import com.DriverMileageTracker.Backend.Enum.ReportStatus;

import java.util.List;

public interface MonthlyReportService {
    List<MonthlyReportDTO> getAllReports();
    MonthlyReportDTO getReportById(Long id);
    MonthlyReportDTO createReport(Long userId, int month, int year);
    MonthlyReportDTO updateReport(Long id, MonthlyReportDTO reportDTO);
    void deleteReport(Long id);

    MonthlyReportDTO updateStatus(Long reportId, ReportStatus status);

    List<MonthlyReportDTO> getPendingReports();

    List<MonthlyReportDTO> getReportsByUserId(Long userId);
}
