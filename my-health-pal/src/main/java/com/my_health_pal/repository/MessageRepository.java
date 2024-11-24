package com.my_health_pal.repository;

import com.my_health_pal.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
      List<Message> findMessagesBySession_Id(Long id);

      @Query(value = "SELECT content FROM messages WHERE sender = :sender AND session_id = :sessionId", nativeQuery = true)
      List<String> findMessageContentsBySenderAndSession(@Param("sender") String sender, @Param("sessionId") Long sessionId);
}
