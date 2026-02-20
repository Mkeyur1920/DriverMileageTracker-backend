package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.Dto.LoginResponse;
import com.DriverMileageTracker.Backend.Dto.RegisterDto;

import javax.security.sasl.AuthenticationException;

public interface AuthService {
    LoginResponse authenticate(String phoneNumber, String vehicleNumber, String password) throws AuthenticationException;

    LoginResponse refreshAccessToken(String refreshToken) throws AuthenticationException;

    RegisterDto register(RegisterDto registerDto);

}
