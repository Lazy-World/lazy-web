package com.lady.messenger.controller;

import com.lady.messenger.entity.Message;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.MessageRepository;
import com.lady.messenger.service.interfaces.MessageService;
import com.lady.messenger.service.interfaces.UserService;
import com.lady.messenger.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/messages")
    public String getChatMessages(@RequestParam(value = "sel", required = false) Long selectedId, Model model) {
        Iterable<Message> messages = messageService.getMessagesByChatId(selectedId);

        model.addAttribute("users", userService.findAll());
        model.addAttribute("messages", messages);

        return "messages";
    }

    @PostMapping("/messages")
    public String addNewPost(@AuthenticationPrincipal User user, @Valid Message message,
                             @RequestParam(value = "sel") Long selectedId,
                             BindingResult bindingResult, Model model
    ) {
        message.setAuthor(user);
        message.setChatId(selectedId);

        model.addAttribute("errorMap", null);
        model.addAttribute("myMessage", null);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrorMap(bindingResult);

            model.addAttribute("errorMap", errorMap);
            model.addAttribute("myMessage", message);
        } else {
            messageRepository.save(message);
        }

        Iterable<Message> messages = messageRepository.findMessagesByChatId(selectedId);
        model.addAttribute("messages", messages);

        return "redirect:/messages?sel=" + selectedId;
    }
}
