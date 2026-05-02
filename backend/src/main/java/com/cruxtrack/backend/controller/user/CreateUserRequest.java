package com.cruxtrack.backend.controller.user;

public record CreateUserRequest(
		String username,
		String firstName,
		String lastName,
		String password,
		String role) {
}
