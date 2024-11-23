package com.my_health_pal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient.
 */
@Configuration
public class WebClientConfig {

    @Value("${flask.api.url}")
    private String flaskApiUrl;

    /**
     * Creates a WebClient bean with the base URL of the Flask API.
     *
     * @return Configured WebClient instance.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(flaskApiUrl)
                .build();
    }
}

