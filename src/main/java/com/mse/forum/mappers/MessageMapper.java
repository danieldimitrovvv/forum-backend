package com.mse.forum.mappers;

import com.mse.forum.dto.MessageDTO;
import com.mse.forum.persistance.entities.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

	MessageEntity toEntity(MessageDTO dto);

	@Mapping(source = "sender.id", target = "senderId")
	@Mapping(source = "receiver.id", target = "receiverId")
	MessageDTO toDto(MessageEntity entity);

}
