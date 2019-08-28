package org.bootcamp.trashhunter.models;

import javax.persistence.*;

@Entity
@Table(name = "statisitcs")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long numOfRatings;

    @Column
    private double summaryScore;

    @Column
    private long numOfDeals;

    @Column
    private long summaryWeight;

    public Statistics() {
    }

    public Statistics(long numOfRatings, double summaryScore, long numOfDeals, long summaryWeight) {
        this.numOfRatings = numOfRatings;
        this.summaryScore = summaryScore;
        this.numOfDeals = numOfDeals;
        this.summaryWeight = summaryWeight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getNumOfDeals() {
        return numOfDeals;
    }

    public void setNumOfDeals(long numOfDeals) {
        this.numOfDeals = numOfDeals;
    }

    public long getSummaryWeight() {
        return this.summaryWeight;
    }

    public void setSummaryWeight(long summaryWeight) {
        this.summaryWeight = summaryWeight;
    }
}
