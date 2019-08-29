package org.bootcamp.trashhunter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userFromId;

    private long userToId;

    private boolean vote;

    public Vote() {
    }

    public Vote(long userFromId, long userToId, boolean vote) {
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.vote = vote;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(long userFromId) {
        this.userFromId = userFromId;
    }

    public long getUserToId() {
        return userToId;
    }

    public void setUserToId(long userToId) {
        this.userToId = userToId;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }
}
