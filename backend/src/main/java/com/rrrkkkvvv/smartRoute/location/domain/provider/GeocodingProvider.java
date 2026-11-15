package com.rrrkkkvvv.smartRoute.location.domain.provider;


import com.rrrkkkvvv.smartRoute.location.domain.model.Location;

import java.util.List;

public interface GeocodingProvider {
    List<Location> search(String query);
}
