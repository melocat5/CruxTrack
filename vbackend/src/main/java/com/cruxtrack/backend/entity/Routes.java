package com.cruxtrack.backend.entity;

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
    private String name;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private String holdColor;

    @Column(nullable = false)
    private boolean isActive;
    

    public Routes() {
    }

    public Routes(String name, String grade, String holdColor, boolean isActive) {
        this.name = name;
        this.grade = grade;
        this.holdColor = holdColor;
        this.isActive = isActive;
    }


    //Getters

    public long getRouteId() {
        return routeId;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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


}
