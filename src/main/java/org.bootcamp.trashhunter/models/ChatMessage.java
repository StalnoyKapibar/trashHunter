package org.bootcamp.trashhunter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private MessageType messageType;

    private String content;

    private String sender;

    private String trashType;

    private LocalDateTime created;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_fk", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sender senderObject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taker_fk", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Taker taker;

    public ChatMessage(MessageType messageType, String content, String sender, LocalDateTime created, String trashType, Sender senderObject, Taker taker) {
        this.messageType = messageType;
        this.content = content;
        this.sender = sender;
        this.senderObject = senderObject;
        this.created = created;
        this.taker = taker;
        this.trashType = trashType;
    }

    public ChatMessage() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sender getSenderObject() {
        return senderObject;
    }

    public void setSenderObject(Sender senderObject) {
        this.senderObject = senderObject;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Taker getTaker() {
        return taker;
    }

    public void setTaker(Taker taker) {
        this.taker = taker;
    }

    public String getTrashType() {
        return trashType;
    }

    public void setTrashType(String trashType) {
        this.trashType = trashType;
    }

    public MessageType getType() {
        return messageType;
    }

    public void setType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public enum MessageType {
        CHAT, JOIN, LEAVE, OFFER, LASTOFFER
    }
}
