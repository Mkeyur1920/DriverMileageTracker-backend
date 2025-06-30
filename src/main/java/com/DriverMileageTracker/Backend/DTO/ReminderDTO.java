package com.DriverMileageTracker.Backend.DTO;

import com.DriverMileageTracker.Backend.Database.Users;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
//@Builder
public class ReminderDTO {
    private Long id;
    private Long userId;
    private String reminderText;
    private LocalDate dueDate;
    private boolean isCompleted;


}