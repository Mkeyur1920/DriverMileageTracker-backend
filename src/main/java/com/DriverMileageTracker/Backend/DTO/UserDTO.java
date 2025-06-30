package com.DriverMileageTracker.Backend.DTO;
import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
//@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String vehicleNumber;
    private List<ReminderDTO> reminders;
    private Set<RoleDTO> roles;

    private String password;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
