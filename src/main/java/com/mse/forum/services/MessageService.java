package com.mse.forum.services;

import com.mse.forum.dto.ConversationDTO;
import com.mse.forum.dto.MessageDTO;
import com.mse.forum.dto.RecentDTO;

import java.util.List;

public interface MessageService {

	boolean save(MessageDTO dto);

	List<MessageDTO> getMessagesByReceiverId(Long id);

	List<MessageDTO> getMessagesBySenderId(Long id);

	List<ConversationDTO>  getConversationByReceiverId(Long receiverId);

	RecentDTO getLastRecentMessageByReceiverId(Long id);

	List<RecentDTO> getAllLastRecentMessages();

	void messageIsSeen(Long messageId);
}
