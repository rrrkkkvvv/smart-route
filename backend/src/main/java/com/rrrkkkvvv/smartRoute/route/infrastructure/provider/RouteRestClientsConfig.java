package com.rrrkkkvvv.smartRoute.route.infrastructure.provider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class RouteRestClientsConfig {
    @Value("${heigit-api.api-key:}")
    private String heigtApiKey;

    @Bean
    @Qualifier("odtRestClient")
    public RestClient odtRestClient(){
        return RestClient.builder()
                .baseUrl("https://api.odt.org.pl/api/planner/v1")
                .build();
    }
    @Bean
    @Qualifier("heigitRestClient")
    @ConditionalOnExpression("!'${heigit-api.api-key:}'.isEmpty()")
    public RestClient heigtRestClient(){
        return RestClient.builder()
                .baseUrl("https://api.heigit.org/openrouteservice/v2")
                .defaultHeader(HttpHeaders.AUTHORIZATION, heigtApiKey)
                .build();
    }
}
