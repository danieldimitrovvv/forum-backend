package com.mse.forum.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mse.forum.persistance.entities.ScoreEntity;

public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {
	
	@Query(value = "SELECT s FROM scores s "+ 
					"JOIN replies r ON r.id = s.reply.id JOIN users u ON u.id = s.user.id " + 
					"WHERE r.id = :replyId and u.id = :userId")
	List<ScoreEntity> getScoreByReplyIdAndUserId(@Param("replyId") Long replyId, @Param("userId") Long userId);
	
	@Query(value = "SELECT count(s) FROM scores s "+ 
			"JOIN replies r ON r.id = s.reply.id " + 
			"WHERE r.id = :replyId AND score > 0")
	Long getPositiveScoreCountByReplyId(@Param("replyId") Long replyId);
	
	@Query(value = "SELECT count(s) FROM scores s "+ 
			"JOIN replies r ON r.id = s.reply.id " + 
			"WHERE r.id = :replyId AND score < 0")
	Long getNegativeScoreCountByReplyId(@Param("replyId") Long replyId);
}
