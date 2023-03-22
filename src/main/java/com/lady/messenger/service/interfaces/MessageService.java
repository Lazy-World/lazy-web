package com.lady.messenger.service.interfaces;

import com.lady.messenger.entity.Message;

public interface MessageService {
    Iterable<Message> getMessagesByChatId(Long id);
}