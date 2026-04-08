package com.cruxtrack.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//This creates the table for ropes

@Entity
@Table(name = "ropes")
public class Ropes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ropeId;

    @Column()
    private int usageCount;

    @Column()
    private LocalDate usageDate;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean flipped;

    @Column(nullable = false)
    private boolean isRetired;

    public Ropes() {
    }

    public Ropes(int usageCount, LocalDate usageDate, boolean active, boolean flipped, boolean isRetired) {
        this.usageCount = usageCount;
        this.usageDate = usageDate;
        this.active = active;
        this.flipped = flipped;
        this.isRetired = isRetired;
    }


    //Getters

    public Long getRopeId() {
        return ropeId;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public boolean isRetired() {
        return isRetired;
    }

    //Setters

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }  

    public void setRetired(boolean isRetired) {
        this.isRetired = isRetired;
    }



}
