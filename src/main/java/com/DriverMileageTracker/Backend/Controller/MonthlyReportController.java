package com.DriverMileageTracker.Backend.Controller;

import com.DriverMileageTracker.Backend.DTO.MileageRecordDTO;
import com.DriverMileageTracker.Backend.DTO.MonthlyReportDTO;
import com.DriverMileageTracker.Backend.Enum.ReportStatus;
import com.DriverMileageTracker.Backend.Services.MileageRecordService;
import com.DriverMileageTracker.Backend.Services.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-reports")
public class MonthlyReportController {
    @Autowired
    private MonthlyReportService monthlyReportService;

    @Autowired
    private MileageRecordService mileageRecordService;

    @GetMapping("/all")
    public List<MonthlyReportDTO> getAll() {
        return monthlyReportService.getAllReports();
    }

    @GetMapping("/{id}")
    public MonthlyReportDTO getById(@PathVariable Long id) {
        return monthlyReportService.getReportById(id);
    }

    @GetMapping("/create")
    public ResponseEntity<MonthlyReportDTO> generateMonthlyReport(
            @RequestParam Long userId,
            @RequestParam int month,
            @RequestParam int year
    ) {
        MonthlyReportDTO monthlyReportDTO = monthlyReportService.createReport(userId, month, year);
        return ResponseEntity.ok(monthlyReportDTO);
    }

    @PutMapping("/{id}")
    public MonthlyReportDTO update(@PathVariable Long id, @RequestBody MonthlyReportDTO dto) {
        return monthlyReportService.updateReport(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        monthlyReportService.deleteReport(id);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<MonthlyReportDTO>> getPendingReports() {
        return ResponseEntity.ok(monthlyReportService.getPendingReports());
    }

    @PutMapping("/{reportId}/status")
    public ResponseEntity<MonthlyReportDTO> updateReportStatus(
            @PathVariable Long reportId,
            @RequestParam ReportStatus status
    ) {
        return ResponseEntity.ok(monthlyReportService.updateStatus(reportId, status));
    }
}
