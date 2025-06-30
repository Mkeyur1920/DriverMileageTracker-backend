package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.DTO.ReminderDTO;
import com.DriverMileageTracker.Backend.Database.Reminder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReminderMapper {

    @Mapping(source = "user.id",target = "userId")
    ReminderDTO toDto(Reminder reminder);

    @Mapping(target = "user.id",source = "userId")
    Reminder toEntity(ReminderDTO reminderDTO);




}
