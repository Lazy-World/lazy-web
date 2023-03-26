package com.lady.messenger.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import javax.validation.Valid;
import java.util.Set;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {
    boolean success;
    @JsonAlias("error-codes")
    Set<String> errorCodes;
}
