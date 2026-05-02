package com.cruxtrack.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cruxtrack.backend.controller.user.ChangePasswordRequest;
import com.cruxtrack.backend.controller.user.CreateUserRequest;
import com.cruxtrack.backend.controller.user.UpdateProfileRequest;
import com.cruxtrack.backend.controller.user.UserProfileDto;
import com.cruxtrack.backend.user.AppUser;
import com.cruxtrack.backend.user.Role;
import com.cruxtrack.backend.user.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/me")
	public ResponseEntity<UserProfileDto> getMe() {
		return userRepository.findByUsername(currentUsername())
				.map(u -> ResponseEntity.ok(toDto(u)))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PutMapping("/me")
	public ResponseEntity<UserProfileDto> updateMe(@RequestBody UpdateProfileRequest body) {
		AppUser u = userRepository.findByUsername(currentUsername())
				.orElseThrow(() -> new IllegalStateException("Not logged in"));
		if (body.preferredName() != null) {
			u.setPreferredName(body.preferredName());
		}
		return ResponseEntity.ok(toDto(userRepository.save(u)));
	}

	@PostMapping("/me/change-password")
	public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest body) {
		if (body == null || body.current() == null || body.newPassword() == null) {
			return ResponseEntity.badRequest().build();
		}
		AppUser u = userRepository.findByUsername(currentUsername())
				.orElseThrow(() -> new IllegalStateException("Not logged in"));
		if (!passwordEncoder.matches(body.current(), u.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		u.setPassword(passwordEncoder.encode(body.newPassword()));
		userRepository.save(u);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public List<UserProfileDto> listUsers() {
		return userRepository.findAll().stream().map(this::toDto).toList();
	}

	@PostMapping
	public ResponseEntity<UserProfileDto> createUser(@RequestBody CreateUserRequest req) {
		if (req == null || req.username() == null || req.username().isBlank() || req.password() == null) {
			return ResponseEntity.badRequest().build();
		}
		if (userRepository.existsByUsername(req.username())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		Role role = parseRole(req.role());
		AppUser u = new AppUser(
				req.username(),
				passwordEncoder.encode(req.password()),
				role,
				req.firstName() != null ? req.firstName() : "",
				req.lastName() != null ? req.lastName() : "");
		u.setActive(true);
		return ResponseEntity.ok(toDto(userRepository.save(u)));
	}

	@PutMapping("/{id}/deactivate")
	public ResponseEntity<Void> deactivate(@PathVariable Long id) {
		return userRepository.findById(id)
				.<ResponseEntity<Void>>map(u -> {
					u.setActive(false);
					userRepository.save(u);
					return ResponseEntity.noContent().build();
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	private static String currentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null ? auth.getName() : null;
	}

	private UserProfileDto toDto(AppUser u) {
		return new UserProfileDto(
				u.getId(),
				u.getUsername(),
				u.getFirstName() != null ? u.getFirstName() : "",
				u.getLastName() != null ? u.getLastName() : "",
				u.getPreferredName() != null ? u.getPreferredName() : "",
				u.getRole().name(),
				u.isActive());
	}

	private static Role parseRole(String r) {
		if (r == null) {
			return Role.USER;
		}
		try {
			return Role.valueOf(r.toUpperCase());
		} catch (IllegalArgumentException ex) {
			return Role.USER;
		}
	}
}
