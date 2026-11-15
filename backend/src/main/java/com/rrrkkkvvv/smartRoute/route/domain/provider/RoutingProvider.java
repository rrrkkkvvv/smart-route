package com.rrrkkkvvv.smartRoute.route.domain.provider;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;
import com.rrrkkkvvv.smartRoute.route.domain.model.Route;

import java.util.List;

public interface RoutingProvider {
    List<Route > findRoutes(RoutingProviderRequest request);
    boolean supports(RouteProfile profile);

}
