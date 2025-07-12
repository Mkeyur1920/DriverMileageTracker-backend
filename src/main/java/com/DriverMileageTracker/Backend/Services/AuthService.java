package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.Dto.RegisterDto;
import com.DriverMileageTracker.Backend.Dto.UserDTO;

import javax.security.sasl.AuthenticationException;

public interface AuthService {
    UserDTO authenticate(String phoneNumber, String vehicleNumber,String password) throws AuthenticationException;

    RegisterDto register(RegisterDto registerDto);

}
