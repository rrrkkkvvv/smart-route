package com.rrrkkkvvv.smartRoute.location.api;


import com.rrrkkkvvv.smartRoute.location.application.LocationService;
import com.rrrkkkvvv.smartRoute.location.domain.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Location> > searchLocation(@RequestParam String query){

        return ResponseEntity.status(200).body(locationService.searchLocation(query));
    }
}
