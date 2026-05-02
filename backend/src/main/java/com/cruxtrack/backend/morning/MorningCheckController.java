package com.cruxtrack.backend.morning;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/morning-checks")
public class MorningCheckController {

	private final MorningCheckSubmissionRepository submissionRepository;

	public MorningCheckController(MorningCheckSubmissionRepository submissionRepository) {
		this.submissionRepository = submissionRepository;
	}

	@GetMapping
	public List<MorningCheckSubmission> list() {
		return submissionRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<MorningCheckSubmission> submit(@RequestBody(required = false) MorningCheckPostBody body) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Instant ts = Instant.now();
		if (body != null && body.timestamp() != null && !body.timestamp().isBlank()) {
			try {
				ts = Instant.parse(body.timestamp());
			} catch (Exception ignored) {
				ts = Instant.now();
			}
		}
		String notes = body != null && body.notes() != null ? body.notes() : "";
		MorningCheckSubmission saved = submissionRepository.save(new MorningCheckSubmission(ts, notes, username));
		return ResponseEntity.ok(saved);
	}
}
