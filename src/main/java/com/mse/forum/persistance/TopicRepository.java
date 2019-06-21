package com.mse.forum.persistance;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mse.forum.persistance.entities.TopicEntity;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

	TopicEntity findByTitle(String title);
	
	@Query(value = "SELECT t FROM topics t")
	List<TopicEntity> getTopicsByPageAndSize(PageRequest pageable);

}
