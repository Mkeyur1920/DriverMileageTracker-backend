package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.DTO.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUserProfile(Long userId, String name, String phoneNumber, String vehicleNumber,
                              String password, byte[] signature, byte[] userPhoto) throws IOException;
    void deleteUser(Long id);
}
