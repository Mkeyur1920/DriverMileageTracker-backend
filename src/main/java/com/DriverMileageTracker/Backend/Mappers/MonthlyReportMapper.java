package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.Dto.MonthlyReportDTO;
import com.DriverMileageTracker.Backend.Database.MonthlyReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlyReportMapper {
//    @Mapping(source = "user.id", target = "userId")
    MonthlyReportDTO toDTO(MonthlyReport monthlyReport);

//    @Mapping(source = "userId", target = "user.id")
    MonthlyReport toEntity(MonthlyReportDTO dto);
}
