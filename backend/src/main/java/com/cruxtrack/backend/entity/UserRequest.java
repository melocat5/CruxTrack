package com.cruxtrack.backend.entity;

import java.time.LocalDate;

public class RopesRequest {
    private String status;
    private int usesChange;
    private int totalUses;
    private String notes;
    private LocalDate usageDate;

    public RopesRequest() {
    }

    public RopesRequest(String status, int usesChange, int totalUses, String notes, LocalDate usageDate) {
        this.status = status;
        this.usesChange = usesChange;
        this.totalUses = totalUses;
        this.notes = notes;
        this.usageDate = usageDate;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsesChange(int usesChange)
    {
        this.usesChange = usesChange;
    }
    public int getUsesChange()
    {
        return usesChange;
    }

    public int getTotalUses()
    {
        return totalUses;
    }

    public void setTotalUses(int totalUses)
    {
        this.totalUses = totalUses;
    }

    /*
    public int getUsesChange() {
        return usesChange;
    }

    public void setUsesChange(int usesChange) {
        this.usesChange = usesChange;
    }

    public int getTotalUses() {
        return totalUses + getUsesChange();
    }

    public void setTotalUses(int totalUses) {
        this.totalUses += getUsesChange();
    }
    
    
    */
   
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }
}