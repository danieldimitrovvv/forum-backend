package com.mse.forum.services;

import java.util.List;

import com.mse.forum.dto.ScoreDTO;

public interface ScoreService {

	boolean save(ScoreDTO dto);

	List<ScoreDTO> getByReplyId(Long id);
	
	List<ScoreDTO> getByReplyIdAndUserId(Long replyId);

	Long getPositiveScoreCountByReplyId(Long replyId);
	
	Long getNegativeScoreCountByReplyId(Long replyId);
	
	void updateScore(ScoreDTO dto);
	
	boolean delete(Long id);
}
