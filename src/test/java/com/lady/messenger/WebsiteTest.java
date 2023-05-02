package com.lady.messenger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
@TestPropertySource(properties = {
        "logging.level.root=WARN",
        "logging.level.com.lady.messenger.WebsiteTest=WARN",
        "logging.level.com.lady.messenger.MyLogger=INFO"
})
public class WebsiteTest {
    private static final Logger LOG = Logger.getLogger("com.lady.messenger.MyLogger");

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOkResponseOnStartPageForGuest() throws Exception {
        LOG.info("\n********** Is website available **********");

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Guest")));

        LOG.info("Result: PASS");
        LOG.info("********** ******************** **********");
    }

    @Test
    public void testAccessToMessagesDeniedForGuest() throws Exception {
        LOG.info("\n********** Messages are not for guests **********");

        this.mockMvc.perform(get("/messages"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        LOG.info("Result: PASS");
        LOG.info("********** *************************** **********");
    }

    @Test
    @Sql(value = {"/sql-before-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql-after-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSuccessLogIn() throws Exception {
        LOG.info("\n********** Admin can log in **********");

        this.mockMvc.perform(formLogin().user("dev").password("root"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        LOG.info("Result: PASS");
        LOG.info("********** **************** **********");
    }

    @Test
    public void testBadCredentialsForNonexistentUser() throws Exception {
        LOG.info("\n********** Fake user can not log in **********");

        // Second way of performing login
        this.mockMvc.perform(post("/login").param("dev", "fake_password"))
                .andExpect(status().isForbidden());

        LOG.info("Result: PASS");
        LOG.info("********** ************************ **********");
    }
}