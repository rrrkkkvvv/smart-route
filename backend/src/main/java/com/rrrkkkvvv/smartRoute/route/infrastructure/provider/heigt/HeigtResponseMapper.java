package com.rrrkkkvvv.smartRoute.route.infrastructure.provider.heigt;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;
import com.rrrkkkvvv.smartRoute.route.domain.model.RoadRoute;
import com.rrrkkkvvv.smartRoute.route.domain.model.Route;
import com.rrrkkkvvv.smartRoute.route.domain.model.RouteSummary;
import com.rrrkkkvvv.smartRoute.route.domain.provider.GeometryEncoder;
import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeigtResponseMapper {
    private  final GeometryEncoder geometryEncoder;

    public HeigtResponseMapper(GeometryEncoder geometryEncoder) {
        this.geometryEncoder = geometryEncoder;
    }

    public List<Route> toRoutes(HeigtApiResponse response, RouteProfile profile){

        String geometry = geometryEncoder.encode(
                response.features().getFirst().geometry().coordinates()
                        .stream()
                        .map(point ->
                                new Coordinate(
                                        point.get(1),
                                        point.getFirst()
                                )
                        ).toList()
        );
        RouteSummary summary = new RouteSummary(
                response.features().getFirst().properties().summary().distance(),
                response.features().getFirst().properties().summary().duration()
        );
        Route route = new RoadRoute(
                summary,
                profile,
                geometry
                );
        return List.of(route);
    }
}
