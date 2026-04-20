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
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int usesChange; // +/-Uses

    @Column(nullable = false)
    private int totalUses;

    @Column
    private String notes;

    @Column
    private LocalDate usageDate;

    public Ropes() {
    }

    public Ropes(String status, int usesChange, int totalUses, String notes, LocalDate usageDate) {
        this.status = status;
        this.usesChange = usesChange;
        this.totalUses = totalUses;
        this.notes = notes;
        this.usageDate = usageDate;
    }


    //Getters

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getUsesChange() {
        return usesChange;
    }

    public int getTotalUses() {
        return totalUses;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsesChange(int usesChange) {
        this.usesChange = usesChange;
    }

    public void setTotalUses(int totalUses) {
        this.totalUses = totalUses;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }



}
