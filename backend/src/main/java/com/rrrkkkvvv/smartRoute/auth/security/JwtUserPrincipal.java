package com.rrrkkkvvv.smartRoute.auth.security;


import java.util.UUID;

public record JwtUserPrincipal(
        UUID id,
        String email
) {
}
