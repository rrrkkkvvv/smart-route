package com.rrrkkkvvv.smartRoute.route.domain.provider;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;
import com.rrrkkkvvv.smartRoute.route.domain.model.RoutePoint;

public record RoutingProviderRequest(
        RoutePoint origin,
        RoutePoint destination,
        RouteProfile profile
) {
}
