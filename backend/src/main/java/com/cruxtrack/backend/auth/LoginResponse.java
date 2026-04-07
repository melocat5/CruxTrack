package com.cruxtrack.backend.auth;

public record LoginResponse(boolean success, String message, String role, String redirectUrl) {
}
