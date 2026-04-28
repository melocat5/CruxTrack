package com.cruxtrack.backend.controller;

import com.cruxtrack.backend.entity.Inventory;
import com.cruxtrack.backend.entity.InventoryRequest;
import com.cruxtrack.backend.repository.InventoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public ResponseEntity<Inventory> saveInventoryData(@RequestBody InventoryRequest request) {
        Inventory inventory = new Inventory(
            request.getItemName(),
            request.getQuantity()
        );

        Inventory saved = inventoryRepository.save(inventory);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventory = inventoryRepository.findAll();
        return ResponseEntity.ok(inventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
