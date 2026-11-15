package com.rrrkkkvvv.smartRoute.location.infrastructure;


import com.rrrkkkvvv.smartRoute.location.domain.model.Location;
import com.rrrkkkvvv.smartRoute.location.domain.provider.GeocodingProvider;
import com.rrrkkkvvv.smartRoute.shared.exception.ExternalProviderException;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class NominatimAdapter implements GeocodingProvider {

    private final RestClient restClient;
    private final LocationMapper mapper;
    public NominatimAdapter(@Qualifier("nominatimRestClient") RestClient restClient, LocationMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    @Override
    public List<Location> search(String query) {
        NominatimApiResponse[] response = restClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                            .path("/search")
                                .queryParam("q", query+", Poland")
                                .queryParam("format","json")
                                .queryParam("limit", 5)
                                .queryParam("addressDetails", 1)

                                .build())
                .retrieve()
                .body(NominatimApiResponse[].class);
        if (response == null) {
            throw new ExternalProviderException(
                    "Nominatim API returned an empty response body"
            );
        }
        return Arrays.stream(response)
                .map(mapper::toLocation)
                .toList();

    }
}
