package com.mse.forum.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyScoreInfoDTO {
	private Long positiveCount;
	
	private Long negativeCount;
	
	private ScoreDTO currentUserVote;
}
