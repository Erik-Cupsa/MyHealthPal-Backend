package com.my_health_pal.dto;

import com.my_health_pal.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
      private Message userMessage;
      private Message gptResponse;
}
