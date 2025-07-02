package com.DriverMileageTracker.Backend.Repository;

import com.DriverMileageTracker.Backend.Database.MileageRecord;
import com.DriverMileageTracker.Backend.Database.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MileageRecordRepository extends JpaRepository<MileageRecord,Long> {
    List<MileageRecord> findByUserId(Long userId);

    @Query("SELECT m FROM MileageRecord m WHERE m.user = :user AND MONTH(m.date) = :month AND YEAR(m.date) = :year")
    List<MileageRecord> findByUserAndMonthYear(@Param("user") Users user,
                                               @Param("month") int month,
                                               @Param("year") int year);

}
