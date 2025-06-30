package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.DTO.UserDTO;
import com.DriverMileageTracker.Backend.Mappers.UserMapper;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import com.DriverMileageTracker.Backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return usersRepository.findAll().stream().map(userMapper::toDto).toList();
    }
    public UserDTO getUserById(Long id) {
        return userMapper.toDto(usersRepository.findById(id).orElse(null));
    }
    public UserDTO createUser(UserDTO dto) {
        return userMapper.toDto(usersRepository.save(userMapper.toEntity(dto)));
    }
    public UserDTO updateUser(Long id, UserDTO dto) {
        dto.setId(id);
        return userMapper.toDto(usersRepository.save(userMapper.toEntity(dto)));
    }
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
