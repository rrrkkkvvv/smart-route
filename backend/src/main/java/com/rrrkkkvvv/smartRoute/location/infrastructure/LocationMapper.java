package com.rrrkkkvvv.smartRoute.location.infrastructure;

import com.rrrkkkvvv.smartRoute.location.domain.model.Location;
import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public Location toLocation(NominatimApiResponse nominatimApiResponse){
        return new Location(
                nominatimApiResponse.display_name(),
                new Coordinate(
                        Double.parseDouble(nominatimApiResponse.lat()),
                        Double.parseDouble(nominatimApiResponse.lon() )
                ));
    }
}
