package com.rrrkkkvvv.smartRoute.route.application;

import com.rrrkkkvvv.smartRoute.route.api.dto.RouteRequest;
import com.rrrkkkvvv.smartRoute.route.domain.model.Route;
import com.rrrkkkvvv.smartRoute.route.domain.provider.RoutingProviderRequest;
import com.rrrkkkvvv.smartRoute.route.domain.provider.RoutingProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class RouteService {

    private final List<RoutingProvider> providers;

    public RouteService(List<RoutingProvider> providers) {
        this.providers = providers;
    }
    public List<Route> findRoutes(RouteRequest request) {


        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<List<Route>>> futures = request.profiles()
                    .stream()
                    .map(profile -> {

                        RoutingProvider provider = providers.stream()
                                .filter(p -> p.supports(profile))
                                .findFirst()
                                .orElseThrow(() ->
                                        new UnsupportedOperationException(
                                                "No provider supports this profile"+profile
                                        ));

                        return executor.submit(() ->
                                provider.findRoutes(
                                        new RoutingProviderRequest(
                                                request.origin(),
                                                request.destination(),
                                                profile
                                        )
                                )
                        );
                    })
                    .toList();
            List<Route> result = new ArrayList<>();

            for (Future<List<Route>> future : futures){

                result.addAll(future.get());
            }


            return result;

        } catch (ExecutionException  e) {
            throw new RuntimeException(e);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

    }

}
