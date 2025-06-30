package com.DriverMileageTracker.Backend.DTO;

import lombok.*;

@Setter
@Getter
//@Builder
public class MonthlyReportDTO {
    private Long id;
    private Long userId;
    private String month;
    private int totalKm;
    private String reportFileUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
