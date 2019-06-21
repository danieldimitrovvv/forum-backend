package com.mse.forum.contollers;

import com.mse.forum.dto.ConversationDTO;
import com.mse.forum.dto.MessageDTO;
import com.mse.forum.dto.RecentDTO;
import com.mse.forum.services.MessageService;
import com.mse.forum.utils.WebExceptionUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/messages")
@RolesAllowed(value = { "ADMIN", "USER", "MODERATOR" })
public class MessageController {

	private MessageService service;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public boolean  createMessage(@RequestBody MessageDTO message) {
		return service.save(message);
	}

	@RequestMapping(path="/{receiverId}/", method = RequestMethod.GET)
	public List<ConversationDTO>  getMessagesByReceiverId(@PathVariable Long receiverId) {
		return service.getConversationByReceiverId(receiverId);
	}

	@RequestMapping(path="/last-message/{receiverId}/", method = RequestMethod.GET)
	public RecentDTO getLastRecentMessageByReceiverId(@PathVariable Long receiverId) {
		return service.getLastRecentMessageByReceiverId(receiverId);
	}

	@RequestMapping(path="/last-recent-messages/", method = RequestMethod.GET)
	public List<RecentDTO> getLastRecentMessages() {
		return service.getAllLastRecentMessages();
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		return WebExceptionUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, "error", "unknonwn error occured");
	}
}
