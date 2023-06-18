package com.lady.messenger.controller;

import com.lady.messenger.entity.Role;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("dev")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql-before-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql-after-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserEditForm() throws Exception {
        User testUser = new User("user");
        testUser.setId(2L);

        when(userRepository.findUserById(2L)).thenReturn(testUser);

        this.mockMvc.perform(get("/user/2"))
                .andExpect(authenticated())
                .andExpect(model().attribute("user", testUser))
                .andExpect(model().attribute("roles", Role.values()))
                .andExpect(view().name("userEdit"));
    }

    @Test
    public void testUserSave() throws Exception {
        User testUser = new User("old_username");
        testUser.setId(2L);

        when(userRepository.findUserById(2L)).thenReturn(testUser);

        String form = "ADMIN=on&USER=on";

        this.mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(form)
                    .param("userId", "2")
                    .param("username", "new_username").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));

        User updatedTestUser = new User("new_username");
        updatedTestUser.setId(2L);
        when(userRepository.findUserById(2L)).thenReturn(updatedTestUser);

        this.mockMvc.perform(get("/user/2"))
                .andExpect(authenticated())
                .andExpect(model().attribute("user", updatedTestUser))
                .andExpect(model().attribute("roles", Role.values()))
                .andExpect(view().name("userEdit"));
    }

    @Test
    public void testGetProfile() throws Exception {
        User testUser = new User("dev");
        testUser.setEmail("some@mail.ru");
        when(userRepository.findByUsername("dev")).thenReturn(testUser);

        this.mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", "dev"))
                .andExpect(model().attribute("email", "some@mail.ru"))
                .andExpect(view().name("profile"));
    }

    @Test
    public void testUpdateProfile() throws Exception {
        User testUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        testUser.setEmail("oldemail@example.com");
        testUser.setActivationCode("test_code");
        testUser.setEmail("oldemail@example.com");

        when(userRepository.findByUsername("dev")).thenReturn(testUser);

        this.mockMvc.perform(post("/user/profile")
                        .param("password", "new_pass")
                        .param("email", "newemail@example.com").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        this.mockMvc.perform(get("/activate/" + testUser.getActivationCode()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("messageType", "success"));

        this.mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", "dev"))
                .andExpect(model().attribute("email", "newemail@example.com"))
                .andExpect(view().name("profile"));

        assertTrue(passwordEncoder.matches("new_pass", testUser.getPassword()));
    }
}
