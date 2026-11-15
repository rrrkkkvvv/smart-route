package com.rrrkkkvvv.smartRoute.route.infrastructure.provider.heigt;

import com.rrrkkkvvv.smartRoute.route.domain.enums.RouteProfile;
import com.rrrkkkvvv.smartRoute.route.domain.model.Route;
import com.rrrkkkvvv.smartRoute.route.domain.provider.RoutingProviderRequest;
import com.rrrkkkvvv.smartRoute.route.domain.provider.RoutingProvider;
import com.rrrkkkvvv.smartRoute.route.domain.UnsupportedRouteProfileException;
import com.rrrkkkvvv.smartRoute.shared.exception.ExternalProviderException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@ConditionalOnExpression("!'${heigit-api.api-key:}'.isEmpty()")
public class HeigtAdapter implements RoutingProvider {

    private final RestClient restClient;
    private final HeigtResponseMapper mapper;
    public HeigtAdapter(@Qualifier("heigitRestClient") RestClient restClient, HeigtResponseMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    @Override
    public List<Route> findRoutes(RoutingProviderRequest request) {
        String path = switch (request.profile()) {
            case CAR -> "driving-car";
            case BIKE -> "cycling-regular";
            case WALK -> "foot-walking";
            default -> throw new UnsupportedRouteProfileException(
                    request.profile().toString()
            );
        };

        String start= request.origin().coordinate().longitude() + ","+
                request.origin().coordinate().latitude();
        String end=  request.destination().coordinate().longitude() + ","+
                request.destination().coordinate().latitude();
        try{
            HeigtApiResponse response = restClient.get()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path("/directions/"+path)
                                    .queryParam("start", start)
                                    .queryParam("end", end)
                                    .build())
                    .retrieve()
                    .body(HeigtApiResponse.class);

            if (response == null) {
                throw new ExternalProviderException(
                        "Heigt API returned an empty response body"
                );
            }
            return mapper.toRoutes(response, request.profile());
        }catch(HttpClientErrorException ex){

            String responseBody = ex.getResponseBodyAsString();

            if (responseBody.contains("\"code\":2010")) {
                return List.of();
//                return List.of(
//                        new RoadRoute(
//                            new RouteSummary(0,0),
//                            request.profile(),
//                            ""
//                        )
//                ) ;
            }

            throw ex;
        }

    }

    @Override
    public boolean supports(RouteProfile profile) {
        return
                profile==RouteProfile.CAR
                || profile==RouteProfile.BIKE
                || profile==RouteProfile.WALK;
    }
}
