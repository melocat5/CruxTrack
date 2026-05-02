package com.cruxtrack.backend.controller.user;

public record UserProfileDto(
		Long id,
		String username,
		String firstName,
		String lastName,
		String preferredName,
		String role,
		boolean isActive) {
}
