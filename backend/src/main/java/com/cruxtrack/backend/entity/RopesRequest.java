package com.cruxtrack.backend.entity;

import java.time.LocalDate;

public class RopesRequest {
	private String ropeId;
	private int uses;
	private String notes;
	private LocalDate usageDate;

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
