package com.rrrkkkvvv.smartRoute.route.domain.model;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;

public record RoadRoute(

        RouteSummary summary,
        RouteProfile profile,
        String geometry
) implements Route {
}
