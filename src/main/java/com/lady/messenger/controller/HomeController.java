package com.lady.messenger.controller;

import com.lady.messenger.entity.UpdateLog;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.UpdateLogRepository;
import com.lady.messenger.service.interfaces.HomeService;
import com.lady.messenger.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private UpdateLogRepository updateLogRepository;

    @Autowired
    private HomeService homeService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String getHomePage(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<UpdateLog> updateLogs = homeService.getUpdateLogs(filter);

        model.addAttribute("updateLogs", updateLogs);
        model.addAttribute("filter", filter);

        return "home";
    }

    @PostMapping("/")
    public String addNewPost(@AuthenticationPrincipal User user, @Valid UpdateLog updateLog,
            BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        updateLog.setAuthor(user);

        model.addAttribute("errorMap", null);
        model.addAttribute("message", null);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrorMap(bindingResult);

            model.addAttribute("errorMap", errorMap);
            model.addAttribute("message", updateLog);
        } else {
            homeService.uploadFile(updateLog, uploadPath, file);
            updateLogRepository.save(updateLog);
        }

        Iterable<UpdateLog> messages = updateLogRepository.findAll();
        model.addAttribute("messages", messages);

        return "redirect:/";
    }
}
