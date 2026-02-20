package com.DriverMileageTracker.Backend.Controller.config;

import com.DriverMileageTracker.Backend.Database.Users;
import com.DriverMileageTracker.Backend.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users persistedUser = userRepository.findByPhoneNumber(username);
        if (persistedUser == null) {
            persistedUser = userRepository.findByVehicleNumber(username);
        }

        if (persistedUser == null) {
            throw new UsernameNotFoundException("User not found for identifier: " + username);
        }

        List<GrantedAuthority> authorities = persistedUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()))
                .map(GrantedAuthority.class::cast)
                .toList();

        return new User(username, persistedUser.getPassword(), authorities);
    }
}
