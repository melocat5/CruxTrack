package com.cruxtrack.backend.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// CRUXTRACK: SPRING DATA AUTO-IMPLEMENTS SAVE(), FINDALL(), ETC. WE ADD CUSTOM FINDERS BY METHOD NAME
public interface UserRepository extends JpaRepository<AppUser, Long> {

	// CRUXTRACK: USED AT LOGIN TO LOAD THE USER ROW BY TYPED USERNAME
	Optional<AppUser> findByUsername(String username);

	// CRUXTRACK: OPTIONAL HELPER IF YOU WANT TO CHECK "IS THIS NAME TAKEN?" BEFORE SIGNUP
	boolean existsByUsername(String username);
}
