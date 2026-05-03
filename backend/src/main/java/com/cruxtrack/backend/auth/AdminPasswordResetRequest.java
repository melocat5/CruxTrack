package com.cruxtrack.backend.auth;

public record AdminPasswordResetRequest(
        String targetUsername,
        String adminUsername,
        String adminPassword,
        String newPassword) {
}
