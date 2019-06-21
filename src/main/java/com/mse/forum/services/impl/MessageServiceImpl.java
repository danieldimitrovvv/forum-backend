package com.mse.forum.services.impl;


import com.mse.forum.dto.ConversationDTO;
import com.mse.forum.dto.MessageDTO;
import com.mse.forum.dto.RecentDTO;
import com.mse.forum.mappers.MessageMapper;
import com.mse.forum.mappers.UsersMapper;
import com.mse.forum.persistance.MessagesRepository;
import com.mse.forum.persistance.UsersRepository;
import com.mse.forum.persistance.entities.MessageEntity;
import com.mse.forum.persistance.entities.UserEntity;
import com.mse.forum.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

	private MessagesRepository repository;

	private UsersRepository userRepository;

	private MessageMapper mapper;
	private UsersMapper userMapper;


	@Override
	public boolean save(MessageDTO dto) {
		Optional<UserEntity> receiver = userRepository.findById(dto.getReceiverId());
		if (!receiver.isPresent()) {
			throw new IllegalArgumentException("Tryed to add a message  for non-existant user.");
		}


		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Optional<UserEntity> sender = userRepository.findByUsername(userDetails.getUsername());

		MessageEntity entity = mapper.toEntity(dto);
		entity.setSender(sender.get());
		entity.setReceiver(receiver.get());

		repository.save(entity);
		return true;
	}

	@Override
	public List<MessageDTO> getMessagesByReceiverId(Long receiverId) {
		Optional<UserEntity> receiver = userRepository.findById(receiverId);

		if (!receiver.isPresent()) {
			throw new IllegalArgumentException("Tryed to get a message  for non-existant user.");
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Optional<UserEntity> sender = userRepository.findByUsername(userDetails.getUsername());

		List<MessageEntity> messageEntities =
				repository.findAllBySenderIdAndReceiverId(sender.get().getId(), receiverId);

		return messageEntities.stream().map(entity -> mapper.toDto(entity)).collect(Collectors.toList());
	}

	@Override
	public List<MessageDTO> getMessagesBySenderId(Long senderId) {
		Optional<UserEntity> sender = userRepository.findById(senderId);
		if (!sender.isPresent()) {
			throw new IllegalArgumentException("Tryed to get a message  for non-existant user.");
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Optional<UserEntity> receiver = userRepository.findByUsername(userDetails.getUsername());

		List<MessageEntity> messageEntities = repository.findAllBySenderIdAndReceiverId(senderId, receiver.get().getId());

		return messageEntities.stream().map(entity -> mapper.toDto(entity)).collect(Collectors.toList());
	}

	@Override
	public List<ConversationDTO>  getConversationByReceiverId(Long receiverId) {
		List<MessageDTO> receiveMessage = this.getMessagesByReceiverId(receiverId);
		List<MessageDTO> senderMessage = this.getMessagesBySenderId(receiverId);

		List<ConversationDTO> conversation = new ArrayList<ConversationDTO>(receiveMessage.size() + senderMessage.size());

		receiveMessage.stream().forEach(msg ->
			conversation.add(new ConversationDTO(msg,false)
		));

		senderMessage.stream().forEach(msg -> {
			this.messageIsSeen(msg.getId());
			msg.setHasSeen(true);
			conversation.add(new ConversationDTO(msg, true));
		});

		// sort arrayList by createdOn
		Collections.sort(conversation, new Comparator<ConversationDTO>() {
			public int compare(ConversationDTO msg1, ConversationDTO msg2) {
				return msg1.getMessage().getCreatedOn().compareTo(msg2.getMessage().getCreatedOn());
			}
		});

		return conversation;
	}

	@Override
	public RecentDTO getLastRecentMessageByReceiverId(Long id) {
		Optional<UserEntity> receiver = userRepository.findById(id);

		if (!receiver.isPresent()) {
			throw new IllegalArgumentException("Tryed to get a message  for non-existant user.");
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Optional<UserEntity> sender = userRepository.findByUsername(userDetails.getUsername());

		List<MessageEntity> messageEntities =
				repository.findLastMessageBySenderIdAndReceiverId(sender.get().getId(), id);

		if (messageEntities.size() > 0) {
			return new RecentDTO(mapper.toDto(messageEntities.get(0)), userMapper.toDto(receiver.get()));
		}

		return null;
	}

	@Override
	public List<RecentDTO> getAllLastRecentMessages() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Optional<UserEntity> sender = userRepository.findByUsername(userDetails.getUsername());

		List<MessageEntity> allSentMessages = repository.findAllBySenderId(sender.get().getId());

		List<RecentDTO> recents = new ArrayList<RecentDTO>(allSentMessages.size() + 1);

		allSentMessages
				.stream()
				.forEach(entity -> {
					RecentDTO currentRecent = this.getLastRecentMessageByReceiverId(entity.getReceiver().getId());
					if (!recents.contains(currentRecent)) {
						recents.add(currentRecent);
					}

				});

		return recents;
	}

	@Override
	public void messageIsSeen(Long messageId) {
		Optional<MessageEntity> message = repository.findById(messageId);

		if (!message.isPresent()) {
			throw new IllegalArgumentException("Tryed to update non-existant message.");
		}

		MessageEntity entity = message.get();
		entity.setHasSeen(true);
		repository.save(entity);
	}


}
