package com.rrrkkkvvv.smartRoute.location.integration;

import com.rrrkkkvvv.smartRoute.auth.security.JwtService;
import com.rrrkkkvvv.smartRoute.auth.security.JwtUserPrincipal;
import com.rrrkkkvvv.smartRoute.location.application.LocationService;
import com.rrrkkkvvv.smartRoute.location.domain.model.Location;
import com.rrrkkkvvv.smartRoute.shared.domain.model.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @MockitoBean
    private LocationService locationService;

    private String token;

    @BeforeEach
    void setUp() {
        token = jwtService.buildToken(
                new JwtUserPrincipal(
                        UUID.randomUUID(),
                        "test@example.com"
                )
        );
    }

    @Test
    void searchLocation_shouldReturnOk() throws Exception {
        String query = "Warsaw";

        Location location =
                new Location("Warsaw, Poland", new Coordinate(52.2297, 21.0122));

        when(locationService.searchLocation(query))
                .thenReturn( List.of(location));

        mockMvc.perform(get("/api/locations/")
                        .param("query", query)
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + token
                        ))
                .andExpect(status().isOk());
    }
    @Test
    void searchLocation_shouldReturnBadRequest_whenQueryIsMissing() throws Exception {

        mockMvc.perform(get("/api/locations/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(locationService);
    }
}