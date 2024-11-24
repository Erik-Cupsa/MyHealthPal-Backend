package com.my_health_pal.repository;

import com.my_health_pal.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
      List<Message> findMessagesBySession_Id(Long id);
      List<Message> findMessagesBySender(String sender);
}
