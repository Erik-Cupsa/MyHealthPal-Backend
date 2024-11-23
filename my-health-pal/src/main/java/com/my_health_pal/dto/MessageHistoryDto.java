package com.my_health_pal.dto;

import com.my_health_pal.model.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageHistoryDto {
      private String sender;
      private String content;

      public static MessageHistoryDto fromEntity(Message message) {
            return new MessageHistoryDto(message.getSender(), message.getContent());
      }

      @Override
      public String toString() {
            return "sender='" + sender + '\'' +
                    ", content='" + content + '\'' +
                    '}';
      }
}
