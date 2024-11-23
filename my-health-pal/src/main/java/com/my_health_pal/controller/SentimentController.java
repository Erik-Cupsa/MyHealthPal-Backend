package com.my_health_pal.controller;

import com.my_health_pal.dto.SentimentRequest;
import com.my_health_pal.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// For mapping
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

/**
 * REST controller for sentiment analysis.
 */
@RestController
@RequestMapping("/api/sentiment")
public class SentimentController {

    private final SentimentService sentimentService;

    @Autowired
    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    /**
     * Endpoint to analyze sentiment of provided text.
     *
     * @param request The sentiment analysis request containing text.
     * @return The sentiment result.
     */
    @PostMapping
    public Mono<ResponseEntity<Map<String, String>>> analyzeSentiment(
            @RequestBody SentimentRequest request) {
        return sentimentService.analyzeSentiment(request.getText())
                .map(sentiment -> ResponseEntity.ok(Collections.singletonMap("sentiment", sentiment)))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Collections.singletonMap("error", e.getMessage()))
                ));
    }
}
