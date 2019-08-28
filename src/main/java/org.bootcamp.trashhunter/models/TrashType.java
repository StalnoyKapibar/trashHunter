package org.bootcamp.trashhunter.models;

public enum TrashType {

    METAL,
    PAPER,
    FOOD,
    PLASTIC,
    WOOD,
    GLASS;

    public static TrashType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }

}
