package com.cruxtrack.backend.entity;

import java.time.LocalDate;

public class UserRequest {
    private String username;
    private String password;
    private String role;
    private LocalDate usageDate;

    public UserRequest() {
    }

    public UserRequest(String username, String password, String role, LocalDate usageDate) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.usageDate = usageDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }
}
