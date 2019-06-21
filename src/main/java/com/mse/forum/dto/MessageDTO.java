package com.mse.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

	private Long id;
	private Long senderId;
	private Long receiverId;
	private String message;
	private Date createdOn;
	private Date modifiedOn;
	private boolean hasSeen;
}
