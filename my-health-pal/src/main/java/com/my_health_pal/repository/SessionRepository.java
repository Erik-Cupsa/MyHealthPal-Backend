package com.my_health_pal.repository;

import com.my_health_pal.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
      List<Session> findSessionByUser_Id(Long user_id);
}
