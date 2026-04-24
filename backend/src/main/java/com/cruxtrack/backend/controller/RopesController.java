package com.cruxtrack.backend.controller;

import com.cruxtrack.backend.entity.Ropes;
import com.cruxtrack.backend.entity.RopesRequest;
import com.cruxtrack.backend.repository.RopesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ropes")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class RopesController {

    private final RopesRepository ropesRepository;

    public RopesController(RopesRepository ropesRepository) {
        this.ropesRepository = ropesRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveRopesData(@RequestBody RopesRequest request) {
        int computedTotalUses = request.getTotalUses() + request.getUsesChange();

        Ropes ropes = new Ropes(
            request.getStatus(),
            request.getUsesChange(),
            computedTotalUses,
            request.getNotes(),
            request.getUsageDate()
        );

        Ropes saved = ropesRepository.save(ropes);
        return ResponseEntity.ok(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRopesData(@PathVariable long id, @RequestBody RopesRequest request) {
        return ropesRepository.findById(id)
            .map(existing -> {
                int newTotal = existing.getTotalUses() + request.getUsesChange();
                existing.setStatus(request.getStatus());
                existing.setUsesChange(request.getUsesChange());
                existing.setTotalUses(newTotal);
                existing.setNotes(request.getNotes());
                existing.setUsageDate(request.getUsageDate());
                Ropes updated = ropesRepository.save(existing);
                return ResponseEntity.ok(toResponse(updated));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllRopes() {
        List<Ropes> storedRopes = ropesRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Ropes rope : storedRopes) {
            if (rope != null) {
                response.add(toResponse(rope));
            }
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRope(@PathVariable long id) {
        if (ropesRepository.existsById(id)) {
            ropesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Map<String, Object> toResponse(Ropes ropes) {
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("id", ropes.getId());
        response.put("status", ropes.getStatus());
        response.put("usesChange", ropes.getUsesChange());
        response.put("totalUses", ropes.getTotalUses());
        response.put("notes", ropes.getNotes());
        response.put("usageDate", ropes.getUsageDate() != null ? ropes.getUsageDate().toString() : null);
        return response;
    }
}

    