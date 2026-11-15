package com.rrrkkkvvv.smartRoute.auth.api.dto;

import java.util.UUID;

public record AuthenticatedUser(
        UUID id,
        String email,
        String name
) { }
