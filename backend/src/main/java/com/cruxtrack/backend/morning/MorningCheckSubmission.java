package com.cruxtrack.backend.morning;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "morning_check_submissions")
public class MorningCheckSubmission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Instant timestamp;

	@Column(nullable = false, length = 4000)
	private String notes;

	@Column(nullable = false)
	private String submittedBy;

	public MorningCheckSubmission() {
	}

	public MorningCheckSubmission(Instant timestamp, String notes, String submittedBy) {
		this.timestamp = timestamp;
		this.notes = notes;
		this.submittedBy = submittedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
}
