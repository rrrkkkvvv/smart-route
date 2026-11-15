package com.rrrkkkvvv.smartRoute.route.domain.provider;

import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;

import java.util.List;

public interface GeometryEncoder {
    String encode(List<Coordinate> coordinates);
}
