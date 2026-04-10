package com.cruxtrack.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cruxtrack.backend.auth.dto.LoginRequest;
import com.cruxtrack.backend.auth.dto.UserInfoResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// CRUXTRACK: REST ENDPOINTS FOR LOGIN, LOGOUT, AND "WHO AM I" — THEY RETURN JSON, NOT HTML
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	// CRUXTRACK: CHECKS USERNAME + PASSWORD AGAINST THE DATABASE USING USERDETAILSSERVICE
	private final AuthenticationManager authenticationManager;
	// CRUXTRACK: SAVES THE LOGGED-IN USER INTO THE HTTP SESSION SO THE NEXT REQUEST REMEMBERS YOU
	private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

	public AuthController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<UserInfoResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		if (request == null || request.username() == null || request.username().isBlank()
				|| request.password() == null) {
			return ResponseEntity.badRequest().build();
		}
		try {
			// CRUXTRACK: WRAPS THE TWO STRINGS IN A TOKEN OBJECT THAT THE AUTHENTICATION MANAGER UNDERSTANDS
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.username(),
					request.password());
			Authentication authentication = authenticationManager.authenticate(token);
			// CRUXTRACK: SECURITYCONTEXTHOLDER HOLDS "WHO IS LOGGED IN" FOR THIS THREAD/REQUEST
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			// CRUXTRACK: WRITES THAT CONTEXT INTO THE SERVER SESSION (SESSION COOKIE ON THE BROWSER)
			securityContextRepository.saveContext(context, httpRequest, httpResponse);
			UserDetails principal = (UserDetails) authentication.getPrincipal();
			return ResponseEntity.ok(UserInfoResponse.from(principal));
		}
		catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// CRUXTRACK: CLEARS SESSION AND SECURITY CONTEXT SO THE BROWSER IS NO LONGER LOGGED IN
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/me")
	public ResponseEntity<UserInfoResponse> me() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// CRUXTRACK: NOT LOGGED IN OR ANONYMOUS GUEST → 401 UNAUTHORIZED
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetails userDetails)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(UserInfoResponse.from(userDetails));
	}
}
