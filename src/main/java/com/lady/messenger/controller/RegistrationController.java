package com.lady.messenger.controller;

import com.lady.messenger.entity.User;
import com.lady.messenger.service.interfaces.RegistrationService;
import com.lady.messenger.service.interfaces.UserService;
import com.lady.messenger.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final RegistrationService registrationService;

    private final UserService userService;

    @Value("${recaptcha.site-key}")
    private String recaptchaSiteKey;

    @Value("${recaptcha.secret-key}")
    private String recaptchaSecretKey;

    public RegistrationController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);

        return "registration";
    }

    @PostMapping("/registration")
    public String createNewUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String recaptchaResponse,
            @Valid User user, BindingResult bindingResult, Model model
    ) {
        String captchaUrl = String.format(CAPTCHA_URL, recaptchaSecretKey, recaptchaResponse);
        Map<String, String> errorMap = new HashMap<>();

        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);

        if (registrationService.isFieldEmpty(passwordConfirm)) {
            errorMap.put("password2Error", "Поле не может быть пустым");
        }

        if (registrationService.hasStringChanged(user.getPassword(), passwordConfirm)) {
            errorMap.put("passwordError", "Пароли не совпадают");
        }

        if (!registrationService.isCaptchaValid(captchaUrl)) {
            errorMap.put("recaptchaError", "Что-то пошло не так");
        }

        if (bindingResult.hasErrors()) {
            errorMap.putAll(ControllerUtils.getErrorMap(bindingResult));
        }

        if (!errorMap.isEmpty()) {
            model.addAttribute("errorMap", errorMap);
            return "registration";
        }

        if (!userService.createUser(user)) {
            model.addAttribute("message", "Пользователь уже существует!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Пользователь успешно подтверждён!");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Код активации не найден");
        }

        return "login";
    }
}
