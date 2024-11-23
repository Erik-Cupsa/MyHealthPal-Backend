package com.my_health_pal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for sentiment analysis request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentimentRequest {

    private String text;
}
