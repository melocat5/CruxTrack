package com.cruxtrack.backend.entity;

import java.time.LocalDate;

public class RoutesRequest {
    private String routename;
    private String grade;
    private String holdColor;
    private boolean isActive;
    private String routeType;
    private String starttype;
    private String routesetter;
    private LocalDate usageDate;

    public RoutesRequest() {
    }

    public RoutesRequest(String routename, String grade, String holdColor, boolean isActive, String routeType, String starttype, String routesetter, LocalDate usageDate) {
        this.routename = routename;
        this.grade = grade;
        this.holdColor = holdColor;
        this.isActive = isActive;
        this.routeType = routeType;
        this.starttype = starttype;
        this.routesetter = routesetter;
        this.usageDate = usageDate;
    }

    // Getters and Setters
    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHoldColor() {
        return holdColor;
    }

    public void setHoldColor(String holdColor) {
        this.holdColor = holdColor;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getStarttype() {
        return starttype;
    }

    public void setStarttype(String starttype) {
        this.starttype = starttype;
    }

    public String getRoutesetter() {
        return routesetter;
    }

    public void setRoutesetter(String routesetter) {
        this.routesetter = routesetter;
    }
    
    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }
}