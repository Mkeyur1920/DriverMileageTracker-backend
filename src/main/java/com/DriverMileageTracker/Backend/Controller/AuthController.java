package com.DriverMileageTracker.Backend.Controller;


import com.DriverMileageTracker.Backend.Dto.LoginRequest;
import com.DriverMileageTracker.Backend.Dto.LoginResponse;
import com.DriverMileageTracker.Backend.Dto.RefreshTokenRequest;
import com.DriverMileageTracker.Backend.Dto.RegisterDto;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {

        LoginResponse response = authService.authenticate(loginRequest.getPhoneNumber(), loginRequest.getVehicleNumber(),
                loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) throws AuthenticationException {
        LoginResponse response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
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
