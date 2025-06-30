package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.DTO.MonthlyReportDTO;
//import com.DriverMileageTracker.Backend.Mappers.MonthlyReportMapper;
import com.DriverMileageTracker.Backend.Repository.MonthlyReportRepository;
import com.DriverMileageTracker.Backend.Services.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {
    @Autowired
    private MonthlyReportRepository reportRepository;
//    @Autowired
//    private MonthlyReportMapper reportMapper;

    public List<MonthlyReportDTO> getAllReports() {
        return null;
//        return reportRepository.findAll().stream().map(reportMapper::toDTO).toList();
    }
    public MonthlyReportDTO getReportById(Long id) {
        return null;
//        return reportMapper.toDTO(reportRepository.findById(id).orElse(null));
    }
    public MonthlyReportDTO createReport(MonthlyReportDTO dto) {
        return null;
//        return reportMapper.toDTO(reportRepository.save(reportMapper.toEntity(dto)));
    }
    public MonthlyReportDTO updateReport(Long id, MonthlyReportDTO dto) {
        dto.setId(id);
        return null;
//        return reportMapper.toDTO(reportRepository.save(reportMapper.toEntity(dto)));
    }
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }


}
