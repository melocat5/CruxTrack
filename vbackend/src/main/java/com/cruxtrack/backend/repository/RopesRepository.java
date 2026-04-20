package com.cruxtrack.backend.repository;

import com.cruxtrack.backend.entity.Ropes;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RopesRepository extends JpaRepository<Ropes, Long> {

    for (int i = 0; i < 8; i++) {
        char ropeId = (char) ('A' + i); // Generate rope IDs from 'A' to 'H'
        Ropes rope = new Ropes(ropeId, LocalDate.now(), true, false, false);
        save(rope);
    }

    rope.ropeId.setFlipped(rope.setFlipped(user.inputFlipped));
    

    
}