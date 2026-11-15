package com.rrrkkkvvv.smartRoute.route.domain.model;

import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;

public record TransitStop(
        String name,
        Coordinate coordinate
) {
}
