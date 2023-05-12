package com.lady.messenger.controller;

import com.lady.messenger.dto.CaptchaResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @Sql(value = {"/sql-after-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreateNewUser() throws Exception {
        CaptchaResponseDto captchaResponse = new CaptchaResponseDto();
        captchaResponse.setSuccess(true);
        when(restTemplate.postForObject(anyString(), any(), eq(CaptchaResponseDto.class))).thenReturn(captchaResponse);

        this.mockMvc.perform(post("/registration")
                        .param("username", "test_user")
                        .param("password", "test_pass")
                        .param("password2", "test_pass")
                        .param("email", "testuser@example.com")
                        .param("g-recaptcha-response", "test_captcha").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @Sql(value = {"/sql-before-test/activate-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql-after-test/activate-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testActivateUser() throws Exception {
        String activationCode = "test_code";

        this.mockMvc.perform(get("/activate/" + activationCode))
                .andExpect(status().isOk())
                .andExpect(model().attribute("messageType", "success"))
                .andExpect(model().attribute("message", "User successfully activated"))
                .andExpect(view().name("login"));
    }
}

