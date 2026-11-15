package com.rrrkkkvvv.smartRoute.route.infrastructure.provider.odt;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;
import com.rrrkkkvvv.smartRoute.route.domain.model.Route;
import com.rrrkkkvvv.smartRoute.route.domain.provider.RoutingProvider;
import com.rrrkkkvvv.smartRoute.route.domain.provider.RoutingProviderRequest;
import com.rrrkkkvvv.smartRoute.route.domain.UnsupportedRouteProfileException;
import com.rrrkkkvvv.smartRoute.shared.exception.ExternalProviderException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OdtAdapter implements RoutingProvider {

    private final RestClient restClient;
    private final OdtResponseMapper mapper;

    public OdtAdapter(@Qualifier("odtRestClient") RestClient restClient, OdtResponseMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    @Override
    public List<Route> findRoutes(RoutingProviderRequest request) {
        if(request.profile()!= RouteProfile.TRANSIT){
            throw new UnsupportedRouteProfileException(
                    request.profile().toString()
            );
        }
        String from = request.origin().coordinate().latitude() + ", "+
                request.origin().coordinate().longitude();
        String to =  request.destination().coordinate().latitude() + ", "+
                request.destination().coordinate().longitude();
        OdtApiResponse response = restClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/plan/")
                                .queryParam("from", from)
                                .queryParam("to", to)
                                .queryParam("stops", true)
                                .queryParam("geometry", true)
                                .build())

                .retrieve()
                .body(OdtApiResponse.class);

        if (response == null) {

            throw new ExternalProviderException(
                    "Odt API returned an empty response body"
            );
        }
        return mapper.toRoutes(response, request.profile());
    }

    @Override
    public boolean supports(RouteProfile profile) {
        return profile == RouteProfile.TRANSIT;
    }

}

