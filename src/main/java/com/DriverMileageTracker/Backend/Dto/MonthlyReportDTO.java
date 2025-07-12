package com.DriverMileageTracker.Backend.Dto;

import com.DriverMileageTracker.Backend.Enum.ReportStatus;
import lombok.*;

@Setter
@Getter
//@Builder
public class MonthlyReportDTO {
    private Long id;
    private UserDTO user;
    private String month;
    private int totalKm;

    private ReportStatus status;
    private String reportFileUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(int totalKm) {
        this.totalKm = totalKm;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getReportFileUrl() {
        return reportFileUrl;
    }

    public void setReportFileUrl(String reportFileUrl) {
        this.reportFileUrl = reportFileUrl;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
