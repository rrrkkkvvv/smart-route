package com.rrrkkkvvv.smartRoute.location.application;

import com.rrrkkkvvv.smartRoute.location.domain.model.Location;
import com.rrrkkkvvv.smartRoute.location.domain.LocationNotFoundException;
import com.rrrkkkvvv.smartRoute.location.infrastructure.NominatimAdapter;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final NominatimAdapter nominatimProvider;

    public LocationService(NominatimAdapter nominatimProvider) {
        this.nominatimProvider = nominatimProvider;
    }

    public  List<Location> searchLocation(String query){
        List<Location> locationsList = nominatimProvider.search(query);
        if(locationsList.isEmpty()){
            throw new LocationNotFoundException();
        }
        return locationsList;
    }
}
