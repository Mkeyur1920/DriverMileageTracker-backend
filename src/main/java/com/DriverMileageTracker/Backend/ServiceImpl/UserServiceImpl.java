package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.DTO.UserDTO;
import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Mappers.UserMapper;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import com.DriverMileageTracker.Backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDTO getUserById(Long id) {
        UserDTO dto = userMapper.toDto(usersRepository.findById(id).orElse(null));
        if (dto != null) {
            dto.setPassword("");// Prevent sending hashed password to frontend
        }
        return dto;
    }

    public List<UserDTO> getAllUsers() {
        return usersRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserDTO createUser(UserDTO dto) {
        return userMapper.toDto(usersRepository.save(userMapper.toEntity(dto)));
    }
    public UserDTO updateUserProfile(Long userId, String name, String phoneNumber, String vehicleNumber,
                                     String password, byte[] signature, byte[] userPhoto) throws IOException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setVehicleNumber(vehicleNumber);
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password)); // If you're encoding password
        }

        if (signature != null ) {
            user.setSignature(signature); // or store as file if needed
        }

        if (userPhoto != null ) {
            user.setUserPhoto(userPhoto); // or store as file if needed
        }

        usersRepository.save(user);
        return userMapper.toDto(user); // or manually construct UserDTO
    }
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
