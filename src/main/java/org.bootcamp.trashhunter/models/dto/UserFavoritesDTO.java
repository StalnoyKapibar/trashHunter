package org.bootcamp.trashhunter.models.dto;

import org.bootcamp.trashhunter.models.User;

import javax.persistence.*;

@Entity
public class UserFavoritesDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
	private User userboss;

    @ManyToOne()
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
