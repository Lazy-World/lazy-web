package com.lady.messenger.service;

import com.lady.messenger.dto.CaptchaResponseDto;
import com.lady.messenger.service.interfaces.RegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service(value = "RegistrationService")
public class RegistrationServiceImpl implements RegistrationService {
    private final RestTemplate restTemplate;

    public RegistrationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isFieldEmpty(String field) {
        return (field == null || field.isEmpty());
    }

    public boolean isCaptchaValid(String captchaUrl) {
        CaptchaResponseDto captchaResponse = restTemplate.postForObject(captchaUrl, Collections.emptyList(), CaptchaResponseDto.class);
        return captchaResponse != null && captchaResponse.isSuccess();
    }

    public boolean hasChanged(String curValue, String newValue) {
        return curValue != null && !curValue.equals(newValue);
    }
}
