package com.rrrkkkvvv.smartRoute.route.domain.model;

public sealed interface Route permits RoadRoute, TransitRoute {
    RouteSummary summary();
}
