package com.cruxtrack.backend.routes;

public class RoutesRequest {
    private String routeName;
    
    // Changed back to String
    private String grade;
    
    private String holdColor;
    private String climbType;
    private String startType;
    private String routeSetter;
    private String dateSet;
    private boolean isActive;

    public RoutesRequest() {
    }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getHoldColor() { return holdColor; }
    public void setHoldColor(String holdColor) { this.holdColor = holdColor; }

    public String getClimbType() { return climbType; }
    public void setClimbType(String climbType) { this.climbType = climbType; }

    public String getStartType() { return startType; }
    public void setStartType(String startType) { this.startType = startType; }

    public String getRouteSetter() { return routeSetter; }
    public void setRouteSetter(String routeSetter) { this.routeSetter = routeSetter; }

    public String getDateSet() { return dateSet; }
    public void setDateSet(String dateSet) { this.dateSet = dateSet; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}