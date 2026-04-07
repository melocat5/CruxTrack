package com.cruxtrack.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class AuthController {

    private static final String BUILT_IN_USERNAME = "Admin";
    private static final String BUILT_IN_PASSWORD = "admin123";

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(false, "Username and password are required", null,null));
        }

        boolean isValid = BUILT_IN_USERNAME.equals(request.username())
                && BUILT_IN_PASSWORD.equals(request.password());

        if (isValid) {
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", "ADMIN", "dashboard"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Invalid username or password", null, null));
    }
}
