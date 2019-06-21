package com.mse.forum.contollers;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.mse.forum.dto.TopicDTO;
import com.mse.forum.dto.TopicPageDTO;
import com.mse.forum.services.TopicService;
import com.mse.forum.utils.WebExceptionUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@ControllerAdvice
@RolesAllowed(value = { "ADMIN", "USER", "MODERATOR" })
@RequestMapping(path = "/topics")
public class TopicsController {

	private TopicService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<TopicDTO> getAll() {
		List<TopicDTO> result = service.getAll();
		return result;
	}

	@RequestMapping(path = "/title/{title}/", method = RequestMethod.GET)
	public TopicDTO getTopicByTitle(@PathVariable String title) {
		return service.findByTitle(title);
	}

	@RequestMapping(path = "/id/{id}/", method = RequestMethod.GET)
	public TopicDTO getTopicById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@RequestMapping(path = "/page/", method = RequestMethod.GET)
	public TopicPageDTO getTopicsByPageAndSize(@RequestParam(value = "page", defaultValue="0") int page, 
			@RequestParam(value = "size", defaultValue="10") int size) {
		return service.getTopicsByPageAndSize(page, size);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void createTopic(@RequestBody TopicDTO topic) {
		service.saveTopic(topic);
		return;
	}
	
	@RequestMapping(path = "/increment-views-count/", method = RequestMethod.POST, consumes = "application/json")
	public void incrementViewCount(@RequestBody Long topicId) {
		System.out.println("incrementViewCount: TOPIC ID --->>>" + topicId);
		service.incrementViewCount(topicId);
		return;
	}
	

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		return WebExceptionUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, "error", "unknonwn error occured");
	}
	
}
