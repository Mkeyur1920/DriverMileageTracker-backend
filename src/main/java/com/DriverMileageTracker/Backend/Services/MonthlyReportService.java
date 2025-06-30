package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.DTO.MonthlyReportDTO;

import java.util.List;

public interface MonthlyReportService {
    List<MonthlyReportDTO> getAllReports();
    MonthlyReportDTO getReportById(Long id);
    MonthlyReportDTO createReport(MonthlyReportDTO reportDTO);
    MonthlyReportDTO updateReport(Long id, MonthlyReportDTO reportDTO);
    void deleteReport(Long id);
}
