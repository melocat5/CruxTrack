package com.cruxtrack.backend.repository;

import com.cruxtrack.backend.entity.Ropes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RopesRepository extends JpaRepository<Ropes, Long> {
	Optional<Ropes> findTopByOrderByIdDesc();
}