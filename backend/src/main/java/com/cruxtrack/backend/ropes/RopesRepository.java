package com.cruxtrack.backend.ropes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RopesRepository extends JpaRepository<Ropes, Long> {
    
}
