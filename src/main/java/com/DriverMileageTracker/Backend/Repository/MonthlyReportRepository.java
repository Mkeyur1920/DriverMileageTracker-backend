package com.DriverMileageTracker.Backend.Repository;

import com.DriverMileageTracker.Backend.Database.MonthlyReport;
import com.DriverMileageTracker.Backend.Database.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport,Long> {

    List<MonthlyReport> findByUserId(Long userId);
    MonthlyReport findByUserIdAndMonth(Long userId, String month);

    boolean existsByUserAndMonth(Users user, String month);

}
