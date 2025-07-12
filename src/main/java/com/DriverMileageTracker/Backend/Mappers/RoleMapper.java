package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.Dto.RoleDTO;
import com.DriverMileageTracker.Backend.Database.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

//    @Mappings({
//            @Mapping(source = "name",target = "roleName")
//    })
    Role toEntity(RoleDTO dto);

//    @Mappings({
//            @Mapping(source = "roleName", target = "name")
//    })
    RoleDTO toDTO(Role role);
}
