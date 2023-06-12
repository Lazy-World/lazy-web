package com.lady.messenger.service.interfaces;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessageHistory(Chat chat);
}