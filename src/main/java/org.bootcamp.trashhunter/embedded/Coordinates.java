package org.bootcamp.trashhunter.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coordinates {

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public Coordinates() {
    }

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
