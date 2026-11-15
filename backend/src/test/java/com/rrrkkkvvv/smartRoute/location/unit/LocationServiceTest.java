package com.rrrkkkvvv.smartRoute.location.unit;

import com.rrrkkkvvv.smartRoute.location.application.LocationService;
import com.rrrkkkvvv.smartRoute.location.domain.model.Location;
import com.rrrkkkvvv.smartRoute.location.domain.LocationNotFoundException;
import com.rrrkkkvvv.smartRoute.location.infrastructure.NominatimAdapter;
import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private NominatimAdapter nominatimProvider;

    private LocationService locationService;

    @BeforeEach
    void init() {
        this.locationService = new LocationService(
                nominatimProvider
        );
    }

    @Test
    void searchLocation_shouldReturnFirstLocation_whenLocationsFound() {

        String query = "Warsaw";

        Location firstLocation = createTestLocation(
                "Warsaw, Poland",
                52.2297,
                21.0122
        );

        Location secondLocation = createTestLocation(
                "Warsaw, Indiana, USA",
                38.6270,
                -86.1058
        );

        when(nominatimProvider.search(query))
                .thenReturn(List.of(firstLocation, secondLocation));

         List<Location> result = locationService.searchLocation(query);

        assertThat(result.getFirst())
                .isEqualTo(firstLocation);

        verify(nominatimProvider).search(query);
    }

    @Test
    void searchLocation_shouldThrow_whenNoLocationsFound() {

        String query = "NonExistingLocation";

        when(nominatimProvider.search(query))
                .thenReturn(List.of());

        assertThatThrownBy(() ->
                locationService.searchLocation(query)
        )
                .isInstanceOf(LocationNotFoundException.class);

        verify(nominatimProvider).search(query);
    }

    private Location createTestLocation(
            String name,
            double latitude,
            double longitude
    ) {
        return new Location(
                name,
                new Coordinate(
                        latitude,
                        longitude
                )

        );
    }
}