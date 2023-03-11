package com.lady.messenger.controller;

import com.lady.messenger.entity.Message;
import com.lady.messenger.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String getChatMessages(@RequestParam(value = "sel", required = false) Long selectedId, Model model) {
        Iterable<Message> messages = messageService.getMessagesByChatId(selectedId);
        model.addAttribute("messages", messages);

        return "messages";
    }

}
