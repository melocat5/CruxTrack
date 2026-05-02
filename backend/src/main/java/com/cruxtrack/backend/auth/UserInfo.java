package com.cruxtrack.backend.auth;

import java.util.List;

public record UserInfo(String username, List<String> roles, String primaryRole) {
}
