package com.cruxtrack.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//This creates the table for routes

@Entity
@Table(name = "routes")
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long routeId;

    @Column(nullable = false)
    private String routename;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private String holdColor;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private String routeType;

    @Column(nullable = false)
    private String starttype;

    @Column(nullable = false)
    private String routesetter;
    
    @Column
    private LocalDate usageDate;

    public Routes() {
    }

    public Routes(String routename, String grade, String holdColor, boolean isActive, String routeType, String starttype, String routesetter, LocalDate usageDate) {
        this.routename = routename;
        this.grade = grade;
        this.holdColor = holdColor;
        this.isActive = isActive;
        this.routeType = routeType;
        this.starttype = starttype;
        this.routesetter = routesetter;
        this.usageDate = usageDate;
    }


    //Getters

    public long getRouteId() {
        return routeId;
    }

    public String getRoutename() {
        return routename;
    }

    public String getGrade() {
        return grade;
    }

    public String getHoldColor() {
        return holdColor;
    }

    public boolean isActive() {
        return isActive;
    }

    //Setters

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setHoldColor(String holdColor) {
        this.holdColor = holdColor;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public void setStarttype(String starttype) {
        this.starttype = starttype;
    }

    public void setRoutesetter(String routesetter) {
        this.routesetter = routesetter;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }

}
