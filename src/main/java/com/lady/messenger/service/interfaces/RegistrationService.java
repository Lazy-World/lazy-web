package com.lady.messenger.service.interfaces;

public interface RegistrationService {
    boolean isFieldEmpty(String field);

    boolean isCaptchaValid(String captchaUrl);

    boolean hasStringChanged(String curValue, String newValue);
}
