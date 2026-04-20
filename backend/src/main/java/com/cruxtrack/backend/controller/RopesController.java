package com.cruxtrack.backend.controller;

import com.cruxtrack.backend.entity.Ropes;
import com.cruxtrack.backend.entity.RopesRequest;
import com.cruxtrack.backend.repository.RopesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ropes")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class RopesController {

    private final RopesRepository ropesRepository;

    public RopesController(RopesRepository ropesRepository) {
        this.ropesRepository = ropesRepository;
    }

    @PostMapping
    public ResponseEntity<Ropes> saveRopesData(@RequestBody RopesRequest request) {
        Ropes ropes = new Ropes(
            request.getStatus(),
            request.getUsesChange(),
            request.getTotalUses(),
            request.getNotes(),
            request.getUsageDate()
        );
        Ropes saved = ropesRepository.save(ropes);
        return ResponseEntity.ok(saved);
    }
}