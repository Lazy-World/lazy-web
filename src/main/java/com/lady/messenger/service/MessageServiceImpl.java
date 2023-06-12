package com.lady.messenger.service;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.Message;
import com.lady.messenger.service.interfaces.MessageService;
import com.lady.messenger.utils.ControllerUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    public List<Message> getMessageHistory(Chat chat) {
        return chat.getMessageList();
    }
}
