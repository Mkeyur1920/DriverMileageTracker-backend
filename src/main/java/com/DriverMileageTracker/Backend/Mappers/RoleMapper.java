package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.DTO.RoleDTO;
import com.DriverMileageTracker.Backend.Database.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
