package com.cruxtrack.backend.controller;

import java.time.LocalDate;
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

import com.cruxtrack.backend.entity.Ropes;
import com.cruxtrack.backend.entity.RopesRequest;
import com.cruxtrack.backend.repository.RopesRepository;

@RestController
@RequestMapping("/api/ropes")
public class RopesController {

	private final RopesRepository ropesRepository;

	public RopesController(RopesRepository ropesRepository) {
		this.ropesRepository = ropesRepository;
	}

	@PostMapping
	public ResponseEntity<Ropes> create(@RequestBody RopesRequest request) {
		Ropes rope = new Ropes(
				request.getRopeId() != null ? request.getRopeId() : "",
				request.getUses(),
				request.getNotes() != null ? request.getNotes() : "",
				request.getUsageDate() != null ? request.getUsageDate() : LocalDate.now());
		return ResponseEntity.ok(ropesRepository.save(rope));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ropes> update(@PathVariable long id, @RequestBody RopesRequest request) {
		return ropesRepository.findById(id)
				.map(existing -> {
					if (request.getRopeId() != null) {
						existing.setRopeId(request.getRopeId());
					}
					existing.setUses(request.getUses());
					if (request.getNotes() != null) {
						existing.setNotes(request.getNotes());
					}
					if (request.getUsageDate() != null) {
						existing.setUsageDate(request.getUsageDate());
					}
					return ResponseEntity.ok(ropesRepository.save(existing));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<Ropes> list() {
		return ropesRepository.findAll();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		if (ropesRepository.existsById(id)) {
			ropesRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
