package com.cruxtrack.backend.routes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "routes")
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String routeName;

    @Column(nullable = false)
    private String grade;

    @Column
    private String holdColor;

    @Column
    private String climbType;

    @Column
    private String startType;

    @Column
    private String routeSetter;

    @Column
    private String dateSet;

    @Column(nullable = false)
    private boolean isActive = true;

    public Routes() {
    }

    public Routes(String routeName, String grade, String holdColor, String climbType, String startType, String routeSetter, String dateSet) {
        this.routeName = routeName;
        this.grade = grade;
        this.holdColor = holdColor;
        this.climbType = climbType;
        this.startType = startType;
        this.routeSetter = routeSetter;
        this.dateSet = dateSet;
        this.isActive = isActive;
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getRouteName() { return routeName; }
    public String getGrade() { return grade; }
    public String getHoldColor() { return holdColor; }
    public String getClimbType() { return climbType; }
    public String getStartType() { return startType; }
    public String getRouteSetter() { return routeSetter; }
    public String getDateSet() { return dateSet; }

    // --- Setters ---
    public void setId(Long id) { this.id = id; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setHoldColor(String holdColor) { this.holdColor = holdColor; }
    public void setClimbType(String climbType) { this.climbType = climbType; }
    public void setStartType(String startType) { this.startType = startType; }
    public void setRouteSetter(String routeSetter) { this.routeSetter = routeSetter; }
    public void setDateSet(String dateSet) { this.dateSet = dateSet; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
