package com.placement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public PlacementManager placementManager(
            @Value("${placement.data-file:applications.dat}") String dataFile) {
        return new PlacementManager(dataFile);
    }
}