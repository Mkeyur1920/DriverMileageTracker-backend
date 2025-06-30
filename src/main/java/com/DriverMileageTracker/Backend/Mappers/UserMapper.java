package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.DTO.RegisterDto;
import com.DriverMileageTracker.Backend.DTO.RoleDTO;
import com.DriverMileageTracker.Backend.DTO.UserDTO;
import com.DriverMileageTracker.Backend.Database.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import javax.management.relation.Role;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    
    UserDTO toDto(Users user);

    // DTO to Entity
    Users toEntity(UserDTO userDTO);

    // Lists or Sets if needed
    List<UserDTO> toDtoList(List<Users> users);
    List<Users> toEntityList(List<UserDTO> userDTOs);

    Users registerDtoToEntity(RegisterDto dto);

    RegisterDto entityToRegisterDto(Users user);

}
