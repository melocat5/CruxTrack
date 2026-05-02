package com.cruxtrack.backend.morning;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MorningTaskRepository extends JpaRepository<MorningTask, Long> {

	List<MorningTask> findByIsActiveTrueOrderByIdAsc();
}
