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

import com.cruxtrack.backend.entity.Routes;
import com.cruxtrack.backend.entity.RoutesRequest;
import com.cruxtrack.backend.repository.RoutesRepository;

@RestController
@RequestMapping("/api/routes")
public class RoutesController {

	private final RoutesRepository routesRepository;

	public RoutesController(RoutesRepository routesRepository) {
		this.routesRepository = routesRepository;
	}

	@PostMapping
	public ResponseEntity<Routes> create(@RequestBody RoutesRequest request) {
		Routes routes = new Routes(
				request.getRoutename(),
				request.getGrade(),
				request.getHoldColor(),
				request.isActive(),
				request.getRouteType(),
				request.getStarttype(),
				request.getRoutesetter(),
				request.getUsageDate());
		return ResponseEntity.ok(routesRepository.save(routes));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Routes> update(@PathVariable long id, @RequestBody RoutesRequest request) {
		return routesRepository.findById(id)
				.map(existing -> {
					if (request.getRoutename() != null) {
						existing.setRoutename(request.getRoutename());
					}
					if (request.getGrade() != null) {
						existing.setGrade(request.getGrade());
					}
					if (request.getHoldColor() != null) {
						existing.setHoldColor(request.getHoldColor());
					}
					existing.setActive(request.isActive());
					if (request.getRouteType() != null) {
						existing.setRouteType(request.getRouteType());
					}
					if (request.getStarttype() != null) {
						existing.setStarttype(request.getStarttype());
					}
					if (request.getRoutesetter() != null) {
						existing.setRoutesetter(request.getRoutesetter());
					}
					if (request.getUsageDate() != null) {
						existing.setUsageDate(request.getUsageDate());
					}
					return ResponseEntity.ok(routesRepository.save(existing));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Routes>> list() {
		return ResponseEntity.ok(routesRepository.findAll());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		if (routesRepository.existsById(id)) {
			routesRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
