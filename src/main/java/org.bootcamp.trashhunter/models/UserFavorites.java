package org.bootcamp.trashhunter.models;

import javax.persistence.*;

@Entity
@Table(name="userfavorites")
public class UserFavorites {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "userboss_id")
	private long userBoss;

	@Column(name = "usersubject_id")
	private long userSubject;

	public UserFavorites(){}

	public UserFavorites(long userBoss, long userSubject) {
		this.userBoss = userBoss;
		this.userSubject = userSubject;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserBoss() {
		return userBoss;
	}

	public void setUserBoss(long userBoss) {
		this.userBoss = userBoss;
	}

	public long getUserSubject() {
		return userSubject;
	}

	public void setUserSubject(long userSubject) {
		this.userSubject = userSubject;
	}
}
