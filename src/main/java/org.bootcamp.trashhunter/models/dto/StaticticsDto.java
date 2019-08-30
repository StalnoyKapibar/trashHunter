package org.bootcamp.trashhunter.models.dto;


import org.bootcamp.trashhunter.models.Statistics;

public class StaticticsDto {

    private long id;

    private long numOfRatings;

    private double summaryScore;

    private long numOfDeals;

    private double rating;

    private long summaryWeight;

    public StaticticsDto(long id, long numOfRatings, double summaryScore, long numOfDeals, long summaryWeight) {
        this.id = id;
        this.numOfRatings = numOfRatings;
        this.summaryScore = summaryScore;
        this.numOfDeals = numOfDeals;
        this.rating = summaryScore / numOfRatings;
        this.summaryWeight = summaryWeight;
    }

    public static StaticticsDto getStaticticsDto(Statistics statistics) {
        return new StaticticsDto(statistics.getId(), statistics.getNumOfRatings(), statistics.getSummaryScore(),
                statistics.getNumOfDeals(), statistics.getSummaryWeight());
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getSummaryWeight() {
        return summaryWeight;
    }

    public void setSummaryWeight(long summaryWeight) {
        this.summaryWeight = summaryWeight;
    }
}
