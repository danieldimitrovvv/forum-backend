package com.mse.forum.persistance;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mse.forum.persistance.entities.ReplyEntity;

public interface RepliesRepository extends JpaRepository<ReplyEntity, Long> {
	@Query(value = "SELECT r FROM replies r JOIN topics t ON t.id = r.topic.id WHERE r.topic.id = :topicId")
	List<ReplyEntity> getRepliesByPageAndSize(@Param("topicId") Long topicId, Pageable pageable);
	
	List<ReplyEntity> findAllByTopicId(Long topicId, Pageable pageable);
	
}
