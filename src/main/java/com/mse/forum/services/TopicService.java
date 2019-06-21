package com.mse.forum.services;

import java.util.List;

import com.mse.forum.dto.TopicDTO;
import com.mse.forum.dto.TopicPageDTO;

public interface TopicService {

	boolean saveTopic(TopicDTO dto);

	List<TopicDTO> getAll();
	
	TopicPageDTO getTopicsByPageAndSize(int page, int size);

	TopicDTO findByTitle(String title);

	TopicDTO findById(Long id);

	void incrementViewCount(Long topicId);
}
