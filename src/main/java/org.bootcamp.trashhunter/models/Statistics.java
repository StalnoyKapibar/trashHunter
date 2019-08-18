package org.bootcamp.trashhunter.models;

import javax.persistence.*;

@Entity
@Table(name = "statisitcs")
public class Statistics {

    @OneToOne(cascade = {CascadeType.REFRESH})
    private User user;

    @Column
    private long numOfRatings;

    @Column
    private double summaryScore;

    @Column
    private double rating;

    @Column
    private long numOfDeals;


    public Statistics() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(long numOfRatings) {
        this.numOfRatings = numOfRatings;
    }

    public double getSummaryScore() {
        return summaryScore;
    }

    public void setSummaryScore(double summaryScore) {
        this.summaryScore = summaryScore;
    }

    public double getRating() {
        return summaryScore / numOfRatings;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getNumOfDeals() {
        return numOfDeals;
    }

    public void setNumOfDeals(long numOfDeals) {
        this.numOfDeals = numOfDeals;
    }
}
