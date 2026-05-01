package com.cruxtrack.backend.ropes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ropes")
public class Ropes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ropeId;

    @Column(nullable = false)
    private int uses;

    @Column
    private String notes;

    public Ropes() {
    }

    public Ropes(String ropeId, int uses, String notes) {
        this.ropeId = ropeId;
        this.uses = uses;
        this.notes = notes;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public String getRopeId() {
        return ropeId;
    }

    public int getUses() {
        return uses;
    }

    public String getNotes() {
        return notes;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setRopeId(String ropeId) {
        this.ropeId = ropeId;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
