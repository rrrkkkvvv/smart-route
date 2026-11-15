package com.rrrkkkvvv.smartRoute.route.api.dto;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;
import com.rrrkkkvvv.smartRoute.route.domain.model.RoutePoint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RouteRequest(
        @NotNull
        RoutePoint origin,
        @NotNull
        RoutePoint destination,
        @NotNull
        @NotEmpty(message = "profiles should not be empty")
        List<RouteProfile>  profiles
) {
}
