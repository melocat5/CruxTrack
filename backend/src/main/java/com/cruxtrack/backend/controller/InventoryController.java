package com.cruxtrack.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cruxtrack.backend.entity.Inventory;
import com.cruxtrack.backend.entity.InventoryRequest;
import com.cruxtrack.backend.repository.InventoryRepository;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	private final InventoryRepository inventoryRepository;

	public InventoryController(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}

	@PostMapping
	public ResponseEntity<Inventory> create(@RequestBody InventoryRequest request) {
		Inventory inventory = new Inventory(
				request.getItemName(),
				request.getQuantity(),
				request.getNotes() != null ? request.getNotes() : "");
		return ResponseEntity.ok(inventoryRepository.save(inventory));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Inventory> update(@PathVariable long id, @RequestBody InventoryRequest request) {
		return inventoryRepository.findById(id)
				.map(existing -> {
					if (request.getItemName() != null) {
						existing.setItemName(request.getItemName());
					}
					existing.setQuantity(request.getQuantity());
					if (request.getNotes() != null) {
						existing.setNotes(request.getNotes());
					}
					return ResponseEntity.ok(inventoryRepository.save(existing));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Inventory>> list() {
		return ResponseEntity.ok(inventoryRepository.findAll());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		if (inventoryRepository.existsById(id)) {
			inventoryRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
