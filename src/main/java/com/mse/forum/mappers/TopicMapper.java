package com.mse.forum.mappers;

import org.mapstruct.Mapper;

import com.mse.forum.dto.ReplyWithTopicDTO;
import com.mse.forum.dto.TopicDTO;
import com.mse.forum.persistance.entities.TopicEntity;

@Mapper(componentModel = "spring")
public interface TopicMapper {

	TopicEntity toEntity(TopicDTO dto);

	TopicDTO toDto(TopicEntity entity);
	
	TopicEntity toEntityFromReply(ReplyWithTopicDTO dto);
}
