package com.lady.messenger.controller;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.Message;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.ChatRepository;
import com.lady.messenger.repository.MessageRepository;
import com.lady.messenger.repository.UserRepository;
import com.lady.messenger.service.interfaces.MessageService;
import com.lady.messenger.service.interfaces.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("/messages")
    public String getChatMessages(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(value = "sel", required = false) Long selectedId,
            Model model
    ) {
        model.addAttribute("users", userService.getAllUsers());

        if (selectedId == null) {
            return "messages";
        }

        val secondUser = userService.getUserById(selectedId);
        val chatMembers = Arrays.asList(currentUser, secondUser);
        val chat = userService.getChatWithUsers(chatMembers);

        model.addAttribute("messages", chat.getMessageList());

        return "messages";
    }

    @PostMapping("/messages")
    public String addNewPost(@AuthenticationPrincipal User currentUser, @Valid Message message,
             @RequestParam(value = "sel") Long selectedId,
             BindingResult bindingResult, Model model
    ) {
        if (selectedId == null) {
            return "redirect:/messages";
        }

        val secondUser = userService.getUserById(selectedId);
        val chatMembers = Arrays.asList(currentUser, secondUser);
        val chat = userService.getChatWithUsers(chatMembers);

        message.setAuthor(currentUser);
        message.setMessageDateTime(LocalDateTime.now());
        message.setChat(chat);

        chatRepository.save(chat);
        messageRepository.save(message);

        return "redirect:/messages?sel=" + selectedId;
    }
}
