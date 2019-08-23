package org.bootcamp.trashhunter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bootcamp.trashhunter.models.embedded.Coordinates;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_fk", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sender sender;

    @ManyToMany
    @JoinTable(name = "offers_takers",
            joinColumns = @JoinColumn(name = "offer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "taker_id", referencedColumnName = "id"))
    private List<Taker> respondingTakers;

    @Column(nullable = false)
    private long weight;

    @Column(nullable = false)
    private long volume;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TrashType trashType;

    @Embedded
    private Coordinates coordinates;

    @Column(nullable = false)
    private boolean isSorted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatus offerStatus;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Column(nullable = false)
    private String description;

    public Offer() {
    }

    public Offer(Sender sender, long weight, long volume, long price, TrashType trashType,
                 boolean isSorted, OfferStatus offerStatus, LocalDateTime creationDateTime, String description, Coordinates coordinates) {
        this.sender = sender;
        this.weight = weight;
        this.volume = volume;
        this.price = price;
        this.trashType = trashType;
        this.isSorted = isSorted;
        this.offerStatus = offerStatus;
        this.creationDateTime = creationDateTime;
        this.description = description;
        this.coordinates = coordinates;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public void setSorted(boolean sorted) {
        isSorted = sorted;
    }

    public OfferStatus getStatus() {
        return offerStatus;
    }

    public void setStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
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

    public List<Taker> getRespondingTakers() {
        return respondingTakers;
    }

    public void setRespondingTakers(List<Taker> respondingTakers) {
        this.respondingTakers = respondingTakers;
    }

    @Override
    public String toString() {
        return
                "{\"id\": " + id +
                ", \"weight\": " + weight +
                ", \"volume\": " + volume +
                ", \"price\": " + price +
                ", \"trashType\": " + '\"' + trashType +'\"' +
                ", \"offerStatus\": " + '\"' + offerStatus + '\"' +
                        "}";
    }
}
