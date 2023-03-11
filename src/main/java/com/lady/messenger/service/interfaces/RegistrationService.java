package com.lady.messenger.service.interfaces;

public interface RegistrationService {
    boolean isFieldEmpty(String field);

    boolean isCaptchaValid(String captchaUrl);

    boolean hasChanged(String curValue, String newValue);
}
