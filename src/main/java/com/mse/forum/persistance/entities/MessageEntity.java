package com.mse.forum.persistance.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "messages")
public class MessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender_id", nullable = false)
	private UserEntity sender;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receiver_id", nullable = false)
	private UserEntity receiver;

	@Column(name = "message")
	private String message;

	@Column(name = "has_seen")
	private boolean hasSeen;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "modified_on")
	private Date modifiedOn;

	@PrePersist
	public void setInitData () {
		this.hasSeen = false;
		this.setDates();
	}

	private void setDates() {
		this.modifiedOn = new Date();
		this.createdOn = new Date();
	}

	@PreUpdate
	public void updateDates() {
		this.modifiedOn = new Date();
	}

}
