package org.bootcamp.trashhunter.dto;

import org.bootcamp.trashhunter.models.embedded.Coordinates;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.TrashType;

import java.time.LocalDateTime;

public class OfferDto {

    private long id;

    private Coordinates coordinates;

    private long weight;

    private long volume;

    private long price;

    private TrashType trashType;

    private boolean isSorted;

    private LocalDateTime creationDateTime;

    private String description;

    public OfferDto(long id, Coordinates coordinates, TrashType trashType) {
        this.id = id;
        this.coordinates = coordinates;
        this.trashType = trashType;
    }

    public OfferDto(long id, Coordinates coordinates, long weight, long volume, long price, TrashType trashType, boolean isSorted, LocalDateTime creationDateTime, String description) {
        this.id = id;
        this.coordinates = coordinates;
        this.weight = weight;
        this.volume = volume;
        this.price = price;
        this.trashType = trashType;
        this.isSorted = isSorted;
        this.creationDateTime = creationDateTime;
        this.description = description;
    }

    public static OfferDto getDtoIdCoordTrash(Offer offer) {
        return new OfferDto(offer.getId(), offer.getCoordinates(), offer.getTrashType());
    }

    public static OfferDto getDto(Offer offer) {
        return new OfferDto(offer.getId(),
                offer.getCoordinates(),
                offer.getWeight(),
                offer.getVolume(),
                offer.getPrice(),
                offer.getTrashType(),
                offer.isSorted(),
                offer.getCreationDateTime(),
                offer.getDescription()
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public TrashType getTrashType() {
        return trashType;
    }

    public void setTrashType(TrashType trashType) {
        this.trashType = trashType;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public void setSorted(boolean sorted) {
        isSorted = sorted;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
