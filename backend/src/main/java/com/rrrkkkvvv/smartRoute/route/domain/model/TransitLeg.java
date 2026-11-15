package com.rrrkkkvvv.smartRoute.route.domain.model;

import com.rrrkkkvvv.smartRoute.route.domain.enums.TransportMode;

import java.util.List;

public record TransitLeg(
        TransportMode mode,
        String geometry,
        RoutePoint from,
        RoutePoint to,
        List<TransitStop> stops,
        String routeName
) {
}
