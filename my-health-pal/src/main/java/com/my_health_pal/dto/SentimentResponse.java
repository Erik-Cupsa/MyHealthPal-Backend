package com.my_health_pal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for sentiment analysis response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentimentResponse {
    private String sentiment;
}

