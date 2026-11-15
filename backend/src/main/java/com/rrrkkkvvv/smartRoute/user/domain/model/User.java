package com.rrrkkkvvv.smartRoute.user.domain.model;

import java.util.UUID;

public record User(
        UUID id, String email, String name, String passwordHash
) {
}
