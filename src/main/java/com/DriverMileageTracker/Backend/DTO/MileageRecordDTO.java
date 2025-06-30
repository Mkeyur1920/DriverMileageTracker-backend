package com.DriverMileageTracker.Backend.DTO;

import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDate;


//@Builder
public class MileageRecordDTO {
    private Long id;
    private Long userId;

    private UserDTO user;
    private LocalDate date;
    private int startKm;
    private int endKm;
    private int totalKm;
    private String photoUrl;
    private String placesVisited;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStartKm() {
        return startKm;
    }

    public void setStartKm(int startKm) {
        this.startKm = startKm;
    }

    public int getEndKm() {
        return endKm;
    }

    public void setEndKm(int endKm) {
        this.endKm = endKm;
    }

    public int getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(int totalKm) {
        this.totalKm = totalKm;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPlacesVisited() {
        return placesVisited;
    }

    public void setPlacesVisited(String placesVisited) {
        this.placesVisited = placesVisited;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
