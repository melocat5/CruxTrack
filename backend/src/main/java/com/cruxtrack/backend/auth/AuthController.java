package com.cruxtrack.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class AuthController {

    private static final Map<String, String> BUILT_IN_CREDENTIALS = Map.of(
            "Admin", "admin123",
            "User", "user123"
    );

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(false, "Username and password are required", null,null));
        }

        String username = request.username().trim();
        String expectedPassword = BUILT_IN_CREDENTIALS.get(username);
        boolean isValid = expectedPassword != null && expectedPassword.equals(request.password());

        if (isValid) {
            String role = "Admin".equals(username) ? "ADMIN" : "USER";
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", role, "dashboard"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Invalid username or password", null, null));
    }
}
