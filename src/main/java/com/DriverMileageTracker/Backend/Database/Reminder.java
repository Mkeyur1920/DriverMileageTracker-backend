package com.DriverMileageTracker.Backend.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reminders")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String reminderText;
    private LocalDate dueDate;
    private boolean isCompleted;

    private Long createdBy;
    private LocalDateTime createdDatetime;
    private Long lastUpdatedBy;
    private LocalDateTime lastUpdatedDatetime;

//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
//    }
}

