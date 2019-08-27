package org.bootcamp.trashhunter.models;

public enum OfferStatus {

    OPEN,
    ACTIVE,
    TAKEN,
    COMPLETE;

    public static OfferStatus getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
