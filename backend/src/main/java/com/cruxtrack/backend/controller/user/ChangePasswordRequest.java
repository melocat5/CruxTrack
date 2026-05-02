package com.cruxtrack.backend.controller.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangePasswordRequest(
		String current,
		@JsonProperty("new") String newPassword) {
}
