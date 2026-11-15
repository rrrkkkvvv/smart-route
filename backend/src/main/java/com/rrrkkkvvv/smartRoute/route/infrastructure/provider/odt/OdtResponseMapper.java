package com.rrrkkkvvv.smartRoute.route.infrastructure.provider.odt;


import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;
import com.rrrkkkvvv.smartRoute.route.domain.enums.TransportMode;

import com.rrrkkkvvv.smartRoute.route.domain.model.*;

import com.rrrkkkvvv.smartRoute.route.domain.model.Route;
import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OdtResponseMapper {


    public List<Route> toRoutes(OdtApiResponse response, RouteProfile profile){

        List<Route> routes= new ArrayList<>();

        response.itineraries().stream()
                .filter(itinerary ->
                        itinerary.legs().size() != 1 &&
                        !itinerary.legs().getFirst().mode().equals("WALK")
                        )
                .forEach(itinerary -> {
            int distance =itinerary.legs().stream().mapToInt(leg->leg.distance_m()).sum();
            RouteSummary summary = new RouteSummary(
                    distance,
                    itinerary.duration_s()
            );
            List<TransitLeg> legs = itinerary.legs()
                    .stream()
                    .map(leg ->
                            new TransitLeg(
                                    mapTransportMode(leg.mode()),
                                    leg.geometry(),
                                    mapRoutePoint(leg.from()),
                                    mapRoutePoint(leg.to()),
                                    mapStops(leg.stops()),
                                    leg.route()!= null?
                                    leg.route().short_name(): null
                            )
                            ).toList();

            Route route = new TransitRoute(
                    summary,
                    profile,
                    legs
            );
            routes.add(route);
        });

        return routes;
    }
    private List<TransitStop> mapStops(List<Stop> stops){
        return stops.stream()
                .map(stop ->
                        new TransitStop(
                                stop.name(),
                                new Coordinate(stop.lat(), stop.lon())
                        )
                ).toList();
    }
    private RoutePoint mapRoutePoint(Point point){
        return new RoutePoint(point.name(), new Coordinate(point.lat(), point.lon()));
    }
    private TransportMode mapTransportMode(String mode) {
        return switch (mode.toLowerCase()) {
            case "bus" -> TransportMode.BUS;
            case "tram" -> TransportMode.TRAM;
            case "subway" -> TransportMode.SUBWAY;
            case "train" -> TransportMode.TRAIN;
            case "walk" -> TransportMode.WALK;
            case "ferry" -> TransportMode.FERRY;
            case "rail" -> TransportMode.RAIL;

            default -> throw new UnsupportedOperationException(
                    "Unsupported ODT transport mode: " + mode
            );
        };
    }
}
