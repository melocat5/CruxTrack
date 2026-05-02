package com.cruxtrack.backend.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	@Column
	private LocalDate usageDate;

	public Ropes() {
	}

	public Ropes(String ropeId, int uses, String notes, LocalDate usageDate) {
		this.ropeId = ropeId;
		this.uses = uses;
		this.notes = notes;
		this.usageDate = usageDate;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRopeId() {
		return ropeId;
	}

	public void setRopeId(String ropeId) {
		this.ropeId = ropeId;
	}

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

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
