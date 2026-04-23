package com.cruxtrack.backend.ropes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ropes")
public class RopesController {

    private final RopesRepository ropesRepository;

    public RopesController(RopesRepository ropesRepository) {
        this.ropesRepository = ropesRepository;
    }

    // GET
    @GetMapping
    public List<Ropes> getAllRopes() {
        return ropesRepository.findAll();
    }

    // POST
    @PostMapping
    public Ropes createRope(@RequestBody Ropes rope) {
        return ropesRepository.save(rope);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Ropes> updateRope(@PathVariable Long id, @RequestBody Ropes ropeDetails) {
        return ropesRepository.findById(id)
                .map(existingRope -> {
                    existingRope.setRopeId(ropeDetails.getRopeId());
                    existingRope.setUses(ropeDetails.getUses());
                    existingRope.setNotes(ropeDetails.getNotes());
                    
                    Ropes updatedRope = ropesRepository.save(existingRope);
                    return ResponseEntity.ok(updatedRope);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRope(@PathVariable Long id) {
        if (ropesRepository.existsById(id)) {
            ropesRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}