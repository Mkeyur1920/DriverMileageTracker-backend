package com.DriverMileageTracker.Backend.Controller;


import com.DriverMileageTracker.Backend.DTO.MileageRecordDTO;
import com.DriverMileageTracker.Backend.Services.MileageRecordService;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public MileageRecordDTO create(@RequestBody MileageRecordDTO dto) {
        return mileageRecordService.createRecord(dto);
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
}
