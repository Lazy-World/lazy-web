package com.lady.messenger.controller;

import com.lady.messenger.entity.UpdateLog;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.UpdateLogRepository;
import com.lady.messenger.service.interfaces.HomeService;
import com.lady.messenger.utils.ControllerUtils;
import lombok.val;
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

@Controller
public class HomeController {
    private final UpdateLogRepository updateLogRepository;

    private final HomeService homeService;

    @Value("${upload.path}")
    private String uploadPath;

    public HomeController(UpdateLogRepository updateLogRepository, HomeService homeService) {
        this.updateLogRepository = updateLogRepository;
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String getHomePage(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        model.addAttribute("updateLogs", homeService.reverseUpdateLogList(
                homeService.getUpdateLogsWithFilter(filter))
        );
        model.addAttribute("filter", filter);

        return "home";
    }

    @GetMapping("/edit")
    public String editUpdateLog(@RequestParam(value = "log") Long selectedId, Model model) {
        model.addAttribute("currentLog", homeService.getUpdateLogById(selectedId));

        return "updateLogEdit";
    }

    @PostMapping("/")
    public String addUpdateLog(@AuthenticationPrincipal User user, @Valid UpdateLog currentLog,
            BindingResult bindingResult, Model model,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (!homeService.isFileValid(file)) {
            model.addAttribute("currentLog", currentLog);
            model.addAttribute("fileError", "Пожалуйста, выберите файл");
            model.addAttribute("updateLogs", homeService.reverseUpdateLogList(
                    homeService.getUpdateLogsWithFilter(filter))
            );

            return "home";
        }

        currentLog.setAuthor(user);

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtils.getErrorMap(bindingResult);

            model.addAttribute("errorMap", errorMap);
            model.addAttribute("updateLog", currentLog);
        } else {
            homeService.uploadFile(currentLog, uploadPath, file);
            updateLogRepository.save(currentLog);
        }

        model.addAttribute("updateLogs", homeService.reverseUpdateLogList(
                homeService.getUpdateLogsWithFilter(filter))
        );

        return "redirect:/";
    }

    @PostMapping("/edit")
    public String saveEditedUpdateLog(@Valid UpdateLog currentLog, BindingResult bindingResult, Model model,
             @RequestParam(value = "log") Long selectedId
    ) {
        UpdateLog selectedLog = homeService.getUpdateLogById(selectedId);

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtils.getErrorMap(bindingResult);

            model.addAttribute("errorMap", errorMap);
            model.addAttribute("currentLog", currentLog);
        } else {
            selectedLog.setTitle(currentLog.getTitle());
            selectedLog.setText(currentLog.getText());
            selectedLog.setFilename(selectedLog.getFilename());

            updateLogRepository.save(selectedLog);
        }

        model.addAttribute("updateLogs", homeService.getAllUpdateLogs());

        return "redirect:/edit?log=" + selectedId;
    }
}
