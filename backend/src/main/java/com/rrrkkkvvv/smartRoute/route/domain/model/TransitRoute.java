package com.rrrkkkvvv.smartRoute.route.domain.model;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;

import java.util.List;

public record TransitRoute(
        RouteSummary summary,
        RouteProfile profile,
        List<TransitLeg> legs

) implements Route { }
