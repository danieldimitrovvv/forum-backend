package com.mse.forum.persistance.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "scores")
public class ScoreEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_seq")
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "reply_id", nullable = false)
	private ReplyEntity reply;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
	@Column(name = "score")
	private Long score;

}
