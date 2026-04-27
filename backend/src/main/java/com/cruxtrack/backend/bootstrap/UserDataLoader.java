package com.cruxtrack.backend.bootstrap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cruxtrack.backend.user.AppUser;
import com.cruxtrack.backend.user.Role;
import com.cruxtrack.backend.user.UserRepository;

// CRUXTRACK: RUNS ON STARTUP — IF NO USERS EXIST YET, INSERTS TWO DEMO ACCOUNTS (FOR LOCAL DEV ONLY)
@Component
@Order(1)
public class UserDataLoader implements ApplicationRunner {

	private final UserRepository users;
	private final PasswordEncoder passwordEncoder;

	public UserDataLoader(UserRepository users, PasswordEncoder passwordEncoder) {
		this.users = users;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(ApplicationArguments args) {
		// CRUXTRACK: IF YOU ALREADY HAVE A DATABASE FILE WITH USERS, SKIP SEEDING
		if (users.count() > 0) {
			return;
		}
		// CRUXTRACK: PASSWORDS BELOW ARE ONLY FOR FIRST RUN — CHANGE THEM IN REAL DEPLOYMENTS
		users.save(new AppUser("admin", passwordEncoder.encode("AdminPass1!"), Role.ADMIN, "System", "Admin"));
		users.save(new AppUser("user", passwordEncoder.encode("UserPass1!"), Role.USER, "Gym", "Staff"));
	}
}
