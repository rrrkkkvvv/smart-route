package com.rrrkkkvvv.smartRoute.route.infrastructure.provider.heigt;


import java.util.List;

public record HeigtApiResponse(
       List<Feature> features
) {
}
record Feature(
        Geometry  geometry,
        Properties properties
) {
}

 record Geometry(
        List<List<Double>> coordinates
){}

record Properties(
        Summary summary
){}

record Summary(
        int distance,
        int duration
) {}