package com.cruxtrack.backend.controller;

import com.cruxtrack.backend.entity.Routes;
import com.cruxtrack.backend.entity.RoutesRequest;
import com.cruxtrack.backend.repository.RoutesRepository;
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
            request.getStarttype(),
            request.getRoutesetter(),
            request.getUsageDate()
        );

        Routes saved = routesRepository.save(routes);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Routes>> getAllRoutes() {
        List<Routes> routes = routesRepository.findAll();
        return ResponseEntity.ok(routes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable long id) {
        if (routesRepository.existsById(id)) {
            routesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
