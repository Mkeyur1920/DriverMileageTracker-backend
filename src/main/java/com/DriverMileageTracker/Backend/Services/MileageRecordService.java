package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.Dto.MileageRecordDTO;

import java.util.List;

public interface MileageRecordService {
    List<MileageRecordDTO> getAllRecords();
    MileageRecordDTO getRecordById(Long id);
    MileageRecordDTO createRecord(MileageRecordDTO recordDTO);

    List<MileageRecordDTO> getListOfMileageRecords(Long userId);
    MileageRecordDTO updateRecord(Long id, MileageRecordDTO recordDTO);
    void deleteRecord(Long id);

    double getTotalKilometer(Long userId);

    List<MileageRecordDTO> getRecordsByUserAndMonth(Long userId, String month);
}
