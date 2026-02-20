package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.Database.MileageRecord;
import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Dto.MileageRecordDTO;
import com.DriverMileageTracker.Backend.Exception.ResourceNotFoundException;
import com.DriverMileageTracker.Backend.Mappers.MileageRecordMapper;
import com.DriverMileageTracker.Backend.Repository.MileageRecordRepository;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MileageRecordServiceImplTest {

    @Mock
    private MileageRecordRepository recordRepository;

    @Mock
    private MileageRecordMapper recordMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MileageRecordServiceImpl mileageRecordService;

    @Test
    void createRecord_shouldCalculateTotalKmAndSave_whenInputIsValid() {
        MileageRecordDTO input = new MileageRecordDTO();
        input.setUserId(1L);
        input.setStartKm(100);
        input.setEndKm(160);

        Users user = new Users();
        user.setId(1L);

        MileageRecord mappedEntity = new MileageRecord();
        MileageRecordDTO expectedDto = new MileageRecordDTO();
        expectedDto.setUserId(1L);
        expectedDto.setTotalKm(60);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recordMapper.toEntity(input)).thenReturn(mappedEntity);
        when(recordRepository.save(any(MileageRecord.class))).thenAnswer(inv -> inv.getArgument(0));
        when(recordMapper.toDTO(any(MileageRecord.class))).thenReturn(expectedDto);

        MileageRecordDTO result = mileageRecordService.createRecord(input);

        ArgumentCaptor<MileageRecord> captor = ArgumentCaptor.forClass(MileageRecord.class);
        verify(recordRepository).save(captor.capture());
        MileageRecord savedRecord = captor.getValue();

        assertEquals(60, savedRecord.getTotalKm());
        assertEquals(1L, savedRecord.getUser().getId());
        assertEquals(60, result.getTotalKm());
    }

    @Test
    void createRecord_shouldThrowException_whenEndKmIsNotGreaterThanStartKm() {
        MileageRecordDTO input = new MileageRecordDTO();
        input.setUserId(1L);
        input.setStartKm(200);
        input.setEndKm(180);

        Users user = new Users();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recordMapper.toEntity(input)).thenReturn(new MileageRecord());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> mileageRecordService.createRecord(input)
        );

        assertEquals("End Kilometer must be greater than Start Kilometer.", ex.getMessage());
        verify(recordRepository, never()).save(any(MileageRecord.class));
    }
}
