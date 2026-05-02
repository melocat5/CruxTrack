package com.cruxtrack.backend.user;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// CRUXTRACK: SPRING SECURITY CALLS THIS DURING LOGIN TO TURN A DATABASE ROW INTO A "USERDETAILS" OBJECT
// CRUXTRACK: USERDETAILS HOLDS USERNAME, HASHED PASSWORD, AND ROLE NAMES (E.G. ROLE_USER, ROLE_ADMIN)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser u = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		if (!u.isActive()) {
			throw new DisabledException("Account is deactivated");
		}
		// CRUXTRACK: .ROLES("ADMIN") ADDS "ROLE_ADMIN" BEHIND THE SCENES — THAT STRING IS WHAT HASROLE() CHECKS
		return User.withUsername(u.getUsername())
			.password(u.getPassword())
			.roles(u.getRole().name())
			.build();
	}
}
