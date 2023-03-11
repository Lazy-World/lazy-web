package com.lady.messenger.service;

import com.lady.messenger.entity.Message;
import com.lady.messenger.entity.UpdateLog;
import com.lady.messenger.repository.MessageRepository;
import com.lady.messenger.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Iterable<Message> getMessagesByChatId(Long chatId) {
        return chatId != null && chatId > 0
                ? messageRepository.findMessagesByChatId(chatId)
                : messageRepository.findAll();
    }
}
