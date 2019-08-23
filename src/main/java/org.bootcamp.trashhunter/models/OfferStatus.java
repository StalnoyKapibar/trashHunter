package org.bootcamp.trashhunter.models;

public enum OfferStatus {

    OPEN,
    SEARCHING,
    ACTIVE,
    COMPLETE;

    public static OfferStatus getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
