package com.DriverMileageTracker.Backend.Mappers;

import com.DriverMileageTracker.Backend.Dto.RegisterDto;
import com.DriverMileageTracker.Backend.Dto.UserDTO;
import com.DriverMileageTracker.Backend.Database.Users;
import org.mapstruct.Mapper;

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
