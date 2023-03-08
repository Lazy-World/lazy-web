package com.lady.messenger.controller;

import com.lady.messenger.entity.User;
import com.lady.messenger.service.FeatureService;
import com.lady.messenger.service.UserService;
import com.lady.messenger.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FeatureService featureService;

    @Autowired
    private UserService userService;

    @Value("${recaptcha.site-key}")
    private String recaptchaSiteKey;

    @Value("${recaptcha.secret-key}")
    private String recaptchaSecretKey;

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String recaptchaResponse,
            @Valid User user, BindingResult bindingResult, Model model
    ) {
        String captchaUrl = String.format(CAPTCHA_URL, recaptchaSecretKey, recaptchaResponse);
        Map<String, String> errorMap = new HashMap<>();

        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);

        if (featureService.isFieldEmpty(passwordConfirm)) {
            errorMap.put("password2Error", "Password confirmation cannot be empty");
        }

        if (featureService.hasChanged(user.getPassword(), passwordConfirm)) {
            errorMap.put("passwordError", "Passwords are different!");
        }

        if (!featureService.isCaptchaValid(captchaUrl)) {
            errorMap.put("recaptchaError", "Captcha can not be empty!");
        }

        if (bindingResult.hasErrors()) {
            errorMap.putAll(ControllerUtils.getErrorMap(bindingResult));
        }

        if (!errorMap.isEmpty()) {
            model.addAttribute("errorMap", errorMap);
            return "registration";
        }

        if (!userService.createUser(user)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code not found");
        }

        return "login";
    }
}
