package com.cruxtrack.backend.auth.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// CRUXTRACK: JSON RETURNED TO THE FRONTEND AFTER LOGIN OR FROM GET /API/AUTH/ME
// CRUXTRACK: "AUTHORITIES" IN SPRING LOOK LIKE "ROLE_ADMIN" — WE STRIP "ROLE_" FOR THE UI
public record UserInfoResponse(String username, Set<String> roles, String primaryRole) {

	public static UserInfoResponse from(UserDetails userDetails) {
		Set<String> roles = userDetails.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.map(a -> a.replaceFirst("^ROLE_", ""))
			.collect(Collectors.toSet());
		// CRUXTRACK: SIMPLE RULE FOR DISPLAY: IF ADMIN IS IN THE SET, SHOW ADMIN; OTHERWISE USER
		String primary = roles.contains("ADMIN") ? "ADMIN" : "USER";
		return new UserInfoResponse(userDetails.getUsername(), roles, primary);
	}
}
