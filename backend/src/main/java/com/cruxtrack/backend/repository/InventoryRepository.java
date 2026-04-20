package com.cruxtrack.backend.repository;

import com.cruxtrack.backend.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}