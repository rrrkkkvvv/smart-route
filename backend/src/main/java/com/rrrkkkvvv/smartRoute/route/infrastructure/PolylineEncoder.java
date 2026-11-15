package com.rrrkkkvvv.smartRoute.route.infrastructure;

import com.rrrkkkvvv.smartRoute.route.domain.provider.GeometryEncoder;
import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolylineEncoder implements GeometryEncoder {
    private static final double PRECISION = 1e5;

    @Override
    public String encode(List<Coordinate> coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        long previousLat = 0;
        long previousLon = 0;

        for (Coordinate coordinate : coordinates) {

            long lat = Math.round(coordinate.latitude() * PRECISION);
            long lon = Math.round(coordinate.longitude() * PRECISION);

            long deltaLat = lat - previousLat;
            long deltaLon = lon - previousLon;

            encodeValue(deltaLat, result);
            encodeValue(deltaLon, result);

            previousLat = lat;
            previousLon = lon;
        }

        return result.toString();
    }

    private void encodeValue(long value, StringBuilder result) {

        value = value < 0
                ? ~(value << 1)
                : (value << 1);

        while (value >= 0x20) {
            int nextValue = (int) ((0x20 | (value & 0x1f)) + 63);

            result.append((char) nextValue);

            value >>= 5;
        }

        result.append((char) (value + 63));
    }

}
