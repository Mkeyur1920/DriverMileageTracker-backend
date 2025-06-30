package com.DriverMileageTracker.Backend.Repository;

import com.DriverMileageTracker.Backend.Database.MileageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MileageRecordRepository extends JpaRepository<MileageRecord,Long> {
    List<MileageRecord> findByUserId(Long userId);
}
