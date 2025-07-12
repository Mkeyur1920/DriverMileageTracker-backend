package com.DriverMileageTracker.Backend.Controller;


import com.DriverMileageTracker.Backend.Dto.LoginRequest;
import com.DriverMileageTracker.Backend.Dto.RegisterDto;
import com.DriverMileageTracker.Backend.Dto.UserDTO;
import com.DriverMileageTracker.Backend.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.security.sasl.AuthenticationException;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody  LoginRequest loginRequest) throws AuthenticationException {

        UserDTO user = authService.authenticate(loginRequest.getPhoneNumber(), loginRequest.getVehicleNumber(),
                loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterDto> login(@RequestBody Map<String, RegisterDto> body) {
        try {
            RegisterDto registerDto = body.get("dto");
            RegisterDto createdUser = authService.register(registerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration failed: " + e.getMessage());
        }
    }
}
