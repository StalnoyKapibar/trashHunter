package org.bootcamp.trashhunter.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Sender sender;

    @Column(nullable = false)
    private long weight;

    @Column(nullable = false)
    private long volume;

    @Column(nullable = false)
    private long price;

//    @Column(nullable = false)
//    private TrashType trashType;

    @Column(nullable = false)
    private boolean isSorted;

    @Column(nullable = false)
    private boolean isClosed;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Column(nullable = false)
    private String description;

    public Offer() {
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

//    public TrashType getTrashType() {
//        return trashType;
//    }

//    public void setTrashType(TrashType trashType) {
//        this.trashType = trashType;
//    }

    public boolean isSorted() {
        return isSorted;
    }

    public void setSorted(boolean sorted) {
        isSorted = sorted;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
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
