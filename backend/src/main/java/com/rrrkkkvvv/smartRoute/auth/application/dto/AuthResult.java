package com.rrrkkkvvv.smartRoute.auth.application.dto;

import com.rrrkkkvvv.smartRoute.user.domain.model.User;

public record AuthResult(User user, String token) {
}
