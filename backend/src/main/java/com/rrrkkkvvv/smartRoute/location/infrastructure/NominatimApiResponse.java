package com.rrrkkkvvv.smartRoute.location.infrastructure;

public record NominatimApiResponse(
        String lat,
        String lon,
        String display_name
) {
}
