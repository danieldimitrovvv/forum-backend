package com.mse.forum.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
	private Long id;
	private Long replyId;
	private Long userId;
	private Long score;
}
