package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.models.ChatMessage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ChatMessageDao extends AbstractDAOImpl<ChatMessage> {

    @PersistenceContext
    protected EntityManager entityManager;

    public List<ChatMessage> getChatByTakerIdAndSenderId(long takerId, long senderId) {
        return entityManager.createQuery("SELECT c  FROM ChatMessage c JOIN FETCH c.taker JOIN FETCH c.senderObject WHERE  c.senderObject.id=:senderId AND c.taker.id=:takerId ORDER BY c.created", ChatMessage.class)
                .setParameter("takerId", takerId)
                .setParameter("senderId", senderId)
                .getResultList();
    }

}
