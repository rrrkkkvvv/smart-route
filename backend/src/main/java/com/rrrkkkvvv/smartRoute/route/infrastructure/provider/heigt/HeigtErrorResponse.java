package com.rrrkkkvvv.smartRoute.route.infrastructure.provider.heigt;

public record HeigtErrorResponse(
        Error error
) {
    public record Error(
            int code,
            String message
    ) {}
}
