package com.lady.messenger.controller;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.Message;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.ChatRepository;
import com.lady.messenger.repository.MessageRepository;
import com.lady.messenger.repository.UserRepository;
import com.lady.messenger.service.interfaces.MessageService;
import com.lady.messenger.service.interfaces.UserService;
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
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("/messages")
    public String getChatMessages(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(value = "sel", required = false) Long selectedId,
            Model model
    ) {
        if (selectedId != null) {
            User user2 = userRepository.findUserById(selectedId);

            List<User> userSet = new ArrayList<>();
            userSet.add(user2);
            userSet.add(currentUser);

            if (userService.existsChatWithUsers(userSet)) {
                Chat chat = userService.getChatWithUsers(userSet);
                model.addAttribute("messages", chat.getMessageList());
            }
        }

        model.addAttribute("users", userRepository.findAll());

        return "messages";
    }

    @PostMapping("/messages")
    public String addNewPost(@AuthenticationPrincipal User currentUser, @Valid Message message,
             @RequestParam(value = "sel") Long selectedId,
             BindingResult bindingResult, Model model
    ) {
        User user2 = userRepository.findUserById(selectedId);

        List<User> userSet = new ArrayList<>();
        userSet.add(user2);
        userSet.add(currentUser);

        Chat chat = userService.getChatWithUsers(userSet);

        message.setAuthor(currentUser);
        message.setMessageDateTime(LocalDateTime.now());
        message.setChat(chat);

        chatRepository.save(chat);
        messageRepository.save(message);

        System.out.println("catt");

        return "redirect:/messages?sel=" + selectedId;
    }
}
