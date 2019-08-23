package org.bootcamp.trashhunter.models;

public class UserFavoritesDTO {
	private User userboss;
	private User usersubject;

	public UserFavoritesDTO(){}

	public UserFavoritesDTO(User userboss, User usersubject) {
		this.userboss = userboss;
		this.usersubject = usersubject;
	}

	public User getUserboss() {
		return userboss;
	}

	public void setUserboss(User userboss) {
		this.userboss = userboss;
	}

	public User getUsersubject() {
		return usersubject;
	}

	public void setUsersubject(User usersubject) {
		this.usersubject = usersubject;
	}
}
