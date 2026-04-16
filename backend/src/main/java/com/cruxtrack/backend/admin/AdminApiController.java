package com.cruxtrack.backend.admin;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// CRUXTRACK: EXAMPLE CONTROLLER FOR FUTURE ADMIN-ONLY FEATURES — ACCESS IS BLOCKED UNLESS ROLE IS ADMIN
@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

	@GetMapping("/status")
	// CRUXTRACK: SIMPLE TEST ENDPOINT TO PROVE /API/ADMIN/** IS PROTECTED BY SPRING SECURITY
	public Map<String, Object> status() {
		return Map.of(
				"scope", "admin",
				"message", "Administrator endpoints can be added here; role is already enforced by Spring Security.");
	}
}
