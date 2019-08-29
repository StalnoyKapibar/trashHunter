package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.ChatMessageDao;
import org.bootcamp.trashhunter.models.ChatMessage;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatMessageService extends AbstractServiceImpl<ChatMessage> {

    @Autowired
    private ChatMessageDao dao;

    public List<ChatMessage> getChatByTakerIdAndSenderId(long takerId, long senderId) {
        return dao.getChatByTakerIdAndSenderId(takerId, senderId);
    }
}
