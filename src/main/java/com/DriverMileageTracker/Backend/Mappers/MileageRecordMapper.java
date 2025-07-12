package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.Dto.MileageRecordDTO;
import com.DriverMileageTracker.Backend.Database.MileageRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MileageRecordMapper {

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
//            @Mapping(source = user,target = "user")
            // Add more mappings here, or remove if not needed
    })
    MileageRecordDTO toDTO(MileageRecord mileageRecord);
    @Mapping(source = "userId",target = "user.id")
    MileageRecord toEntity(MileageRecordDTO dto);


}
