package com.DriverMileageTracker.Backend.Repository;

import com.DriverMileageTracker.Backend.Database.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ReminderRepository extends JpaRepository<Reminder,Long> {
    List<Reminder> findByUserIdAndIsCompletedFalse(Long userId);
}
