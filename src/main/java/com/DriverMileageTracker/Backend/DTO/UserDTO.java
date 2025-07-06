package com.DriverMileageTracker.Backend.DTO;
import jakarta.persistence.Lob;
import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
//@Builder
public class UserDTO {


    private Long id;
    private String name;
    private String Password;
    private String phoneNumber;
    private String vehicleNumber;
    private List<ReminderDTO> reminders;
    private Set<RoleDTO> roles;

    private String token;

    private byte[] signature;

    private byte[] userPhoto;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public List<ReminderDTO> getReminders() {
        return reminders;
    }

    public void setReminders(List<ReminderDTO> reminders) {
        this.reminders = reminders;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
