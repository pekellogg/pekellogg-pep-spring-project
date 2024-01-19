package com.example.repository;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * JPA Repository interface for the Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("FROM Message WHERE posted_by = :account_id")
    List<Message> findAllByPostedBy(@Param("account_id") int accountId);
}
