package com.cruxtrack.backend.auth.dto;

// CRUXTRACK: JSON BODY SENT FROM ANGULAR TO POST /API/AUTH/LOGIN (FIELD NAMES MUST MATCH JSON KEYS)
public record LoginRequest(String username, String password) {
}
