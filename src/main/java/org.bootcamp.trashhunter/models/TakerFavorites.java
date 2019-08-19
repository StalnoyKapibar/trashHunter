package org.bootcamp.trashhunter.models;

import javax.persistence.*;

@Entity
@Table(name = "takerFavorites")
public class TakerFavorites {
	@Column(name = "taker_id")
	private long taker_id;
	@Column(name = "sender_id")
	private long sender_id;

	TakerFavorites(){}

	TakerFavorites(long taker_id, long sender_id){
		this.taker_id = taker_id;
		this.sender_id = sender_id;
	}

	public long getTaker_id() {
		return taker_id;
	}

	public void setTaker_id(long taker_id) {
		this.taker_id = taker_id;
	}

	public long getSender_id() {
		return sender_id;
	}

	public void setSender_id(long sender_id) {
		this.sender_id = sender_id;
	}
}
