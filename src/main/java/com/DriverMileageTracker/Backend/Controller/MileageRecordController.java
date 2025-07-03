package com.DriverMileageTracker.Backend.Controller;


import com.DriverMileageTracker.Backend.DTO.MileageRecordDTO;
import com.DriverMileageTracker.Backend.Exception.ResourceNotFoundException;
import com.DriverMileageTracker.Backend.Services.MileageRecordService;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mileage-records")
public class MileageRecordController {
    @Autowired
    private MileageRecordService mileageRecordService;

    @GetMapping("/all")
    public List<MileageRecordDTO> getAll() {
        return mileageRecordService.getAllRecords();
    }

    @GetMapping("/{id}")
    public MileageRecordDTO getById(@PathVariable Long id) {
        return mileageRecordService.getRecordById(id);
    }

    @GetMapping("/user/{userId}")
    public List<MileageRecordDTO> getListOfMileageRecords(@PathVariable Long userId){
        return mileageRecordService.getListOfMileageRecords(userId);
    }
    @PostMapping("/save")
    public ResponseEntity<?> create(@RequestBody MileageRecordDTO dto) {
        try {
            MileageRecordDTO saved = mileageRecordService.createRecord(dto);
            return ResponseEntity.ok(saved);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("messages", List.of("Mileage record already exists for this date and user.")));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("messages", List.of(ex.getMessage())));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("messages", List.of("Unexpected error: " + ex.getMessage())));
        }
    }


    @PutMapping("/{id}")
    public MileageRecordDTO update(@PathVariable Long id, @RequestBody MileageRecordDTO dto) {
        return mileageRecordService.updateRecord(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mileageRecordService.deleteRecord(id);
    }


    @GetMapping("/get-total-km/{userId}")
    public double getTotalKilometer(@PathVariable Long userId){
        return mileageRecordService.getTotalKilometer(userId);
    }
    @GetMapping("/get-records-by")
    public ResponseEntity<List<MileageRecordDTO>> getMileageRecordsByUserAndMonth(
            @RequestParam Long userId,
            @RequestParam String month // Format: yyyy-MM
    ) {
        List<MileageRecordDTO> records = mileageRecordService.getRecordsByUserAndMonth(userId, month);
        return ResponseEntity.ok(records);
    }
}
