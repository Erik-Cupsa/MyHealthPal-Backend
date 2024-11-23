package com.my_health_pal.service;

import com.my_health_pal.dto.SentimentRequest;
import com.my_health_pal.dto.SentimentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// For error handling
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service to interact with the Flask sentiment analysis API.
 */
@Service
public class SentimentService {

    private static final Logger logger = LoggerFactory.getLogger(SentimentService.class);

    private final WebClient webClient;

    @Autowired
    public SentimentService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Calls the Flask API to analyze sentiment.
     *
     * @param text The text to analyze.
     * @return The sentiment result.
     */
    public Mono<String> analyzeSentiment(String text) {
        SentimentRequest request = new SentimentRequest(text);
        logger.info("Sending text to Flask API for sentiment analysis: {}", text);

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SentimentResponse.class)
                .map(SentimentResponse::getSentiment)
                .doOnNext(sentiment -> logger.info("Received sentiment from Flask API: {}", sentiment))
                .onErrorResume(e -> {
                    logger.error("Error calling Flask API: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to analyze sentiment"));
                });
    }
}

