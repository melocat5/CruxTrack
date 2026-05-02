package com.cruxtrack.backend.morning;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/morning-tasks")
public class MorningTaskController {

	private final MorningTaskRepository morningTaskRepository;

	public MorningTaskController(MorningTaskRepository morningTaskRepository) {
		this.morningTaskRepository = morningTaskRepository;
	}

	@GetMapping
	public List<MorningTask> list() {
		return morningTaskRepository.findByIsActiveTrueOrderByIdAsc();
	}

	@PostMapping
	public ResponseEntity<MorningTask> create(@RequestBody MorningTask task) {
		if (task.getTaskName() == null || task.getTaskName().isBlank()) {
			return ResponseEntity.badRequest().build();
		}
		MorningTask toSave = new MorningTask(task.getTaskName().trim(), task.isActive());
		return ResponseEntity.ok(morningTaskRepository.save(toSave));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (morningTaskRepository.existsById(id)) {
			morningTaskRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
