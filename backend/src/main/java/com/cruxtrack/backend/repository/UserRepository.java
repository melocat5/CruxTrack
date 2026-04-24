package com.cruxtrack.backend.repository;

import com.cruxtrack.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}