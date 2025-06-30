package com.DriverMileageTracker.Backend.Database;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MonthlyReport")
@Getter
@Setter
//@Builder
public class MonthlyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String month; // Format YYYY-MM
    private int totalKm;
    private String reportFileUrl;

    private Long createdBy;
    private LocalDateTime createdDatetime;
    private Long lastUpdatedBy;
    private LocalDateTime lastUpdatedDatetime;
}

