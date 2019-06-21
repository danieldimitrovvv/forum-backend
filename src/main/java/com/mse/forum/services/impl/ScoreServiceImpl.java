package com.mse.forum.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mse.forum.dto.ScoreDTO;
import com.mse.forum.mappers.ScoreMapper;
import com.mse.forum.persistance.RepliesRepository;
import com.mse.forum.persistance.ScoreRepository;
import com.mse.forum.persistance.UsersRepository;
import com.mse.forum.persistance.entities.ReplyEntity;
import com.mse.forum.persistance.entities.ScoreEntity;
import com.mse.forum.persistance.entities.UserEntity;
import com.mse.forum.services.ScoreService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {

	private RepliesRepository replyRepository;
	
	private ScoreRepository scoreRepository;

	private ScoreMapper scoreMapper;


	private UsersRepository userRepository;

	@Override
	public boolean save(ScoreDTO dto) {
		Optional<ReplyEntity> reply = this.replyRepository.findById(dto.getReplyId());
		if (!reply.isPresent()) {
			throw new IllegalArgumentException("Tryed to add a score for non-existant reply.");
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
												.getAuthentication().getPrincipal();

		Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
		
		List<ScoreEntity> soresEntities =
				scoreRepository.getScoreByReplyIdAndUserId(reply.get().getId(), user.get().getId());
		
		// add new record (score)
		if (soresEntities.size() == 0) {
			ScoreEntity entity = scoreMapper.toEntity(dto);
			entity.setReply(reply.get());
			entity.setUser(user.get());
			scoreRepository.save(entity);
		} else { // update exist recrd
			ScoreEntity entity = soresEntities.get(0);
			entity.setScore(dto.getScore());
			scoreRepository.save(entity);
		}
		return true;
	}

	@Override
	public List<ScoreDTO> getByReplyId(Long id) {
		Optional<ReplyEntity> reply = replyRepository.findById(id);
		if (!reply.isPresent()) {
			throw new IllegalArgumentException("Tryed to add a reply for non-existant topic.");
		}
		
		return reply.get()
				.getScores()
				.stream()
				.map(scoreMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ScoreDTO> getByReplyIdAndUserId(Long replyId) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
		
		List<ScoreEntity> soresEntities = this.scoreRepository.getScoreByReplyIdAndUserId(replyId, user.get().getId());

		return soresEntities
				.stream()
				.map(this.scoreMapper::toDto)
				.collect(Collectors.toList()); 
	}

	@Override
	public Long getPositiveScoreCountByReplyId(Long replyId) {
		return scoreRepository.getPositiveScoreCountByReplyId(replyId);
	}

	@Override
	public Long getNegativeScoreCountByReplyId(Long replyId) {
		return scoreRepository.getNegativeScoreCountByReplyId(replyId);
	}

	@Override
	public void updateScore(ScoreDTO dto) {
		Optional<ReplyEntity> reply = this.replyRepository.findById(dto.getReplyId());
		if (!reply.isPresent()) {
			throw new IllegalArgumentException("Tryed to update a score for non-existant reply.");
		}

		ScoreEntity entity = scoreRepository.findById(dto.getId()).get();
		entity.setScore(dto.getScore());
		scoreRepository.save(entity);
	}

	@Override
	public boolean delete(Long id) {
		scoreRepository.deleteById(id);
		return true;
	}
	
}
