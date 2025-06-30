package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.DTO.LoginResponse;
import com.DriverMileageTracker.Backend.DTO.RegisterDto;
import com.DriverMileageTracker.Backend.DTO.UserDTO;
import com.DriverMileageTracker.Backend.Database.Role;
import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Enum.ROLE;
import com.DriverMileageTracker.Backend.Mappers.UserMapper;
import com.DriverMileageTracker.Backend.Repository.RoleRepository;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import com.DriverMileageTracker.Backend.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.beans.Encoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    public UserDTO authenticate(String phoneNumber, String vehicleNumber, String password) throws AuthenticationException {

        Users persistedUser;

        if(phoneNumber!=null){
            persistedUser = usersRepository.findByPhoneNumber(phoneNumber);
        }else{
            persistedUser = usersRepository.findByVehicleNumber(vehicleNumber);
        }

//        boolean isPasswordMatch = (passwordEncoder.matches(password,persistedUser.getPassword()));

        if (!(passwordEncoder.matches(password,persistedUser.getPassword()))) {
            throw new AuthenticationException("Incorrect password");
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(phoneNumber + "|" + vehicleNumber, password);

        UserDTO userDTO = userMapper.toDto(persistedUser);
        userDTO.setToken(authToken.toString());
        return userDTO;
    }

    @Override
    public RegisterDto register(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getRepassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        boolean exists = usersRepository.existsByPhoneNumberOrVehicleNumber(dto.getPhoneNumber(), dto.getVehicleNumber());
        if (exists) {
            throw new RuntimeException("User already exists with provided phone or vehicle number.");
        }

        Users user = userMapper.registerDtoToEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role role = roleRepository.findByRoleName(dto.getSelectedRole().toUpperCase());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        Users savedUser = usersRepository.save(user);
        return userMapper.entityToRegisterDto(savedUser);
    }


}
