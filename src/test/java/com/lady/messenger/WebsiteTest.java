package com.lady.messenger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class WebsiteTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOkResponseOnStartPageForGuest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Guest")));
    }

    @Test
    public void testAccessToMessagesDeniedForGuest() throws Exception {
        this.mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @Sql(value = {"/sql-before-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql-after-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSuccessLogIn() throws Exception {
        this.mockMvc.perform(formLogin().user("dev").password("root"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testBadCredentialsForNonexistentUser() throws Exception {
        // Second way of performing login
        this.mockMvc.perform(post("/login").param("dev", "fake_password"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}