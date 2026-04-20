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
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class AuthController {

    private static final String ADMIN_USERNAME = "Admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String USER_USERNAME = "User";
    private static final String USER_PASSWORD = "user123";

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(false, "Username and password are required", null,null));
        }

        String role = null;
        boolean isValid = false;

        if (ADMIN_USERNAME.equals(request.username()) && ADMIN_PASSWORD.equals(request.password())) {
            isValid = true;
            role = "ADMIN";
        } else if (USER_USERNAME.equals(request.username()) && USER_PASSWORD.equals(request.password())) {
            isValid = true;
            role = "USER";
        }

        if (isValid) {
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", role, "dashboard"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Invalid username or password", null, null));
    }
}
