package com.lady.messenger.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("dev")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql-before-test/create-user.sql", "/sql-before-test/messages.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql-after-test/messages.sql", "/sql-after-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetChatMessages() throws Exception {
        this.mockMvc.perform(get("/messages"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='user-list']/div[@data-id=1]/a").string("dev"));
    }

    @Test
    public void testGetExistingMessageFromChat() throws Exception {
        this.mockMvc.perform(get("/messages").param("sel", "2"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='messages-history']/div[1]/div/div/div[1]/div/p").string("cat from dev"));
    }

    @Test
    public void testAddNewMessage() throws Exception {
        this.mockMvc.perform(post("/messages").param("sel", "2").param("text", "dev did it again").with(csrf()))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());
    }
}