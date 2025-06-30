package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.DTO.MileageRecordDTO;
import com.DriverMileageTracker.Backend.Database.MileageRecord;
import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Exception.ResourceNotFoundException;
import com.DriverMileageTracker.Backend.Mappers.MileageRecordMapper;
import com.DriverMileageTracker.Backend.Repository.MileageRecordRepository;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import com.DriverMileageTracker.Backend.Services.MileageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MileageRecordServiceImpl implements MileageRecordService {
    @Autowired
    private MileageRecordRepository recordRepository;
    @Autowired
    private MileageRecordMapper recordMapper;

    @Autowired
    private UserRepository userRepository;

    public List<MileageRecordDTO> getAllRecords() {
            return recordRepository.findAll().stream().map(recordMapper::toDTO).toList();
    }
    public MileageRecordDTO getRecordById(Long id) {
        return recordMapper.toDTO(recordRepository.findById(id).orElse(null));
    }

    @Override
    public List<MileageRecordDTO> getListOfMileageRecords(Long userId) {
        List<MileageRecord> records =  recordRepository.findByUserId(userId).stream().toList();
        return records.stream().map(recordMapper::toDTO).collect(Collectors.toList());
    }
    public MileageRecordDTO createRecord(MileageRecordDTO dto) {
        Users user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MileageRecord record = recordMapper.toEntity(dto);
        record.setUser(user);
        ;
        record.setDate(LocalDate.now());
        if(dto.getEndKm()>dto.getStartKm()){
            record.setTotalKm(dto.getEndKm()-dto.getStartKm());
        }else {
            throw new ResourceNotFoundException("End Kilometer must be greater than Start Kilometer.");
        }

        MileageRecord saved = recordRepository.save(record);
        return recordMapper.toDTO(saved);
    }



    public MileageRecordDTO updateRecord(Long id, MileageRecordDTO dto) {
        dto.setId(id);
        return recordMapper.toDTO(recordRepository.save(recordMapper.toEntity(dto)));
    }
    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    @Override
    public double getTotalKilometer(Long userId) {
        List<MileageRecord> mileageRecords = recordRepository.findByUserId(userId);
        if(!mileageRecords.isEmpty()) {
            return mileageRecords.stream().mapToDouble(MileageRecord::getTotalKm).sum();
        }
        else{
            return 0;
        }
    }
}
