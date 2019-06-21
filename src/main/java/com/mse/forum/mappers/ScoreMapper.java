package com.mse.forum.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mse.forum.dto.ScoreDTO;
import com.mse.forum.persistance.entities.ScoreEntity;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

	ScoreEntity toEntity(ScoreDTO dto);

	@Mapping(source = "reply.id", target = "replyId")
	@Mapping(source = "user.id", target = "userId")
	ScoreDTO toDto(ScoreEntity entity);

}
