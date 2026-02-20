package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.Dto.RegisterDto;
import com.DriverMileageTracker.Backend.Dto.UserDTO;
import com.DriverMileageTracker.Backend.Database.Role;
import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Controller.config.JwtUtil;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

    @Autowired
    private JwtUtil jwtUtil;

    public UserDTO authenticate(String phoneNumber, String vehicleNumber, String password) throws AuthenticationException {
        String identifier = (phoneNumber != null && !phoneNumber.isBlank())
                ? phoneNumber.trim()
                : (vehicleNumber != null ? vehicleNumber.trim() : "");

        if (identifier.isBlank()) {
            throw new AuthenticationException("Phone number or vehicle number is required.");
        }

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new AuthenticationException("Invalid credentials");
        }

        Users persistedUser = usersRepository.findByPhoneNumber(identifier);
        if (persistedUser == null) {
            persistedUser = usersRepository.findByVehicleNumber(identifier);
        }

        if (persistedUser == null) {
            throw new AuthenticationException("User not found.");
        }

        List<String> roles = persistedUser.getRoles() == null
                ? Collections.emptyList()
                : persistedUser.getRoles().stream().map(Role::getRoleName).toList();

        String token = jwtUtil.generateToken(identifier, roles);

        UserDTO userDTO = userMapper.toDto(persistedUser);
        userDTO.setPassword("");
        userDTO.setToken(token);
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
