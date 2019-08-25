package org.bootcamp.trashhunter.models;

import javax.persistence.*;

@Entity
@Table(name="userfavorites")
public class UserFavorites {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "userboss_id")
	private Long userBoss;

	@Column(name = "usersubject_id")
	private Long userSubject;

	public UserFavorites(){}

	public UserFavorites(Long userBoss, Long userSubject) {
		this.userBoss = userBoss;
		this.userSubject = userSubject;
	}

	public Long getId() {return id;}

	public void setId(Long id) {this.id = id;}

	public Long getUserBoss() {return userBoss;}

	public void setUserBoss(Long userBoss) {this.userBoss = userBoss;}

	public Long getUserSubject() {
		return userSubject;
	}

	public void setUserSubject(Long userSubject) {this.userSubject = userSubject;}
}
