package com.mse.forum.persistance;

import com.mse.forum.persistance.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessagesRepository extends JpaRepository<MessageEntity, Long> {
	@Query(value = "SELECT m FROM messages m " +
			"WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId")
	List<MessageEntity> findAllBySenderIdAndReceiverId(
	        @Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

	@Query(value = "SELECT m FROM messages m " +
			"WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) OR " +
			"(m.sender.id = :receiverId AND m.receiver.id = :senderId) ORDER BY m.createdOn DESC")
	List<MessageEntity> findLastMessageBySenderIdAndReceiverId(
	        @Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    List<MessageEntity> findAllBySenderId(Long senderId);

}
