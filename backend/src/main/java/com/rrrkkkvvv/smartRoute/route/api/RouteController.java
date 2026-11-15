package com.rrrkkkvvv.smartRoute.route.api;

import com.rrrkkkvvv.smartRoute.route.api.dto.RouteRequest;
import com.rrrkkkvvv.smartRoute.route.application.RouteService;
import com.rrrkkkvvv.smartRoute.route.domain.model.Route;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<Route>> searchRoutes(@RequestBody @Valid RouteRequest request){
        return  ResponseEntity.status(200).body(routeService.findRoutes(request));
    }
}
