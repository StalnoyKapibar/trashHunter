package org.bootcamp.trashhunter.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "senderFavorites")
public class SenderFavorites {
	@Column(name = "sender_id")
	private long sender_id;
	@Column(name = "taker_id")
	private long taker_id;

	SenderFavorites(){}

	SenderFavorites(long sender_id, long taker_id){
		this.sender_id = sender_id;
		this.taker_id = taker_id;
	}

	public long getSender_id() {
		return sender_id;
	}

	public void setSender_id(long sender_id) {
		this.sender_id = sender_id;
	}

	public long getTaker_id() {
		return taker_id;
	}

	public void setTaker_id(long taker_id) {
		this.taker_id = taker_id;
	}
}
