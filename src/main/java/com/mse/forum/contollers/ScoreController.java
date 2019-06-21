package com.mse.forum.contollers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mse.forum.dto.ReplyScoreInfoDTO;
import com.mse.forum.dto.ScoreDTO;
import com.mse.forum.services.ScoreService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/scores")
@RolesAllowed(value = { "ADMIN", "USER", "MODERATOR" })
public class ScoreController {

	private ScoreService service;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void createScore(@RequestBody ScoreDTO score) {
		service.save(score);
	}

	@RequestMapping(path ="/update/", method = RequestMethod.POST, consumes = "application/json")
	public void updateScore(@RequestBody ScoreDTO score) {
		service.updateScore(score);
	}
	
	@RequestMapping(path = "/replyid/{id}/", method = RequestMethod.GET)
	public List<ScoreDTO> getScoresByReplyId(@PathVariable Long id) {
		return service.getByReplyId(id);
	}

	@RequestMapping(path = "/reply-score-info/{id}/", method = RequestMethod.GET)
	public ReplyScoreInfoDTO getReplyScoresInfoByReplyId(@PathVariable(name="id") Long replyId) {
		Long positiveCount = service.getPositiveScoreCountByReplyId(replyId);
		Long negativeCount = service.getNegativeScoreCountByReplyId(replyId);
		
		List<ScoreDTO> scores = service.getByReplyIdAndUserId(replyId);
		ScoreDTO score = null;
		
		if (scores.size() > 0) {
			score = scores.get(0);
		}
		
		return new ReplyScoreInfoDTO(positiveCount, negativeCount, score);
	}
	
	@RequestMapping(path = "/delete/{id}/", method = RequestMethod.GET)
	public boolean delete(@PathVariable Long id) {
		return service.delete(id);
	}
	
	@RequestMapping(path = "/replyid-and-userid/{id}/", method = RequestMethod.GET)
	public List<ScoreDTO> getScoresByReplyIdAndUserId(@PathVariable Long id) {
		return service.getByReplyIdAndUserId(id);
	}
}
