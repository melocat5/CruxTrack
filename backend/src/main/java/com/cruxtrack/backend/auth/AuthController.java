package com.cruxtrack.backend.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cruxtrack.backend.user.AppUser;
import com.cruxtrack.backend.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public ResponseEntity<UserInfo> login(
			@RequestBody LoginRequest request,
			HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		if (request == null || request.username() == null || request.password() == null) {
			return ResponseEntity.badRequest().build();
		}
		try {
			UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken(request.username(), request.password());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			securityContextRepository.saveContext(context, httpRequest, httpResponse);
			return ResponseEntity.ok(toUserInfo(authentication));
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/me")
	public ResponseEntity<UserInfo> me() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserDetails)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(toUserInfo(auth));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, null);
		return ResponseEntity.noContent().build();
	}

	// CRUXTRACK: NEW ENDPOINT FOR ADMIN PASSWORD RESET
	@PostMapping("/admin-reset-password")
	public ResponseEntity<?> resetPasswordViaAdmin(@RequestBody AdminPasswordResetRequest request) {
		if (request == null || request.targetUsername() == null || request.adminUsername() == null ||
			request.adminPassword() == null || request.newPassword() == null) {
			return ResponseEntity.badRequest().body("Missing required fields.");
		}

		// 1. Fetch Admin
		Optional<AppUser> adminOpt = userRepository.findByUsername(request.adminUsername());
		if (adminOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin authorization failed.");
		}
		AppUser adminUser = adminOpt.get();

		// 2. Verify Admin Credentials & Role
		if (!passwordEncoder.matches(request.adminPassword(), adminUser.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin authorization failed.");
		}
		if (!adminUser.getRole().name().equals("ADMIN")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not an admin.");
		}

		// 3. Fetch Target User
		Optional<AppUser> targetOpt = userRepository.findByUsername(request.targetUsername());
		if (targetOpt.isEmpty()) {
			return ResponseEntity.badRequest().body("Target user not found.");
		}
		AppUser targetUser = targetOpt.get();

		// 4. Update and Save
		targetUser.setPassword(passwordEncoder.encode(request.newPassword()));
		userRepository.save(targetUser);

		// Return a generic JSON success response
		return ResponseEntity.ok().body("{\"success\": true}");
	}

	private static UserInfo toUserInfo(Authentication auth) {
		List<String> roles = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.map(a -> a.startsWith("ROLE_") ? a.substring(5) : a)
				.toList();
		String primary = roles.contains("ADMIN") ? "ADMIN" : (roles.isEmpty() ? "USER" : roles.get(0));
		return new UserInfo(auth.getName(), roles, primary);
	}
}