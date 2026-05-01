package com.cruxtrack.backend.routes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class RoutesController {

    private final RoutesRepository routesRepository;

    public RoutesController(RoutesRepository routesRepository) {
        this.routesRepository = routesRepository;
    }

    @PostMapping
    public ResponseEntity<Routes> saveRoutesData(@RequestBody RoutesRequest request) {
        Routes routes = new Routes(
            request.getRouteName(),
            request.getGrade(),
            request.getHoldColor(),
            request.getClimbType(),
            request.getStartType(),
            request.getRouteSetter(),
            request.getDateSet(),
            request.isActive()
        );

        Routes saved = routesRepository.save(routes);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Routes>> getAllRoutes() {
        List<Routes> routes = routesRepository.findAll();
        return ResponseEntity.ok(routes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Routes> updateRoute(@PathVariable Long id, @RequestBody RoutesRequest request) {
        return routesRepository.findById(id).map(existingRoute -> {
            existingRoute.setRouteName(request.getRouteName());
            existingRoute.setGrade(request.getGrade());
            existingRoute.setHoldColor(request.getHoldColor());
            existingRoute.setClimbType(request.getClimbType());
            existingRoute.setStartType(request.getStartType());
            existingRoute.setRouteSetter(request.getRouteSetter());
            existingRoute.setDateSet(request.getDateSet());
            existingRoute.setActive(request.isActive());
            
            Routes updated = routesRepository.save(existingRoute);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
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
