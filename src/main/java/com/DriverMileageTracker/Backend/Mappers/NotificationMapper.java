package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.Database.MonthlyReport;
import com.DriverMileageTracker.Backend.Database.Notification;
import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Dto.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface NotificationMapper {


    @Mappings({
            @Mapping(source = "sender.id",target = "senderId"),
            @Mapping(source = "receiver.id",target = "receiverId"),
            @Mapping(source = "relatedReport.id",target = "relatedReportId")
    })

    NotificationDTO toDto(Notification notification);

    default Notification toEntity(NotificationDTO dto, Users sender, Users receiver, MonthlyReport report) {
        Notification n = new Notification();
        n.setTitle(dto.getTitle());
        n.setMessage(dto.getMessage());
        n.setType(dto.getType());
        n.setStatus(dto.getStatus() != null ? dto.getStatus() : "UNREAD");
        n.setSender(sender);
        n.setReceiver(receiver);
        n.setRelatedReport(report);
        return n;
    }
}
