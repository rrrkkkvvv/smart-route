package com.rrrkkkvvv.smartRoute.route.infrastructure.provider.odt;

import java.util.List;

public record OdtApiResponse (
        List<Itinerary> itineraries
){ }
record Itinerary(
        int duration_s,
        List<Leg> legs
){ }

record Leg(
        String mode,
        String geometry,
        Point from,
        Point to,
        int distance_m,
        Route route,
        List<Stop> stops
) {}

record Point(
        String name,
        double lat,
        double lon
){}
record Stop(
        String name,
        double lat,
        double lon
){}
record Route(
        String short_name
){}