package com.lady.messenger.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.logging.Logger;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithUserDetails("dev")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql-before-test/create-user.sql", "/sql-before-test/update-log.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql-after-test/update-log.sql", "/sql-after-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource(properties = {
        "logging.level.root=WARN",
        "logging.level.com.lady.messenger.WebsiteTest=WARN",
        "logging.level.com.lady.messenger.MyLogger=INFO"
})
public class HomeControllerTest {
    private static final Logger LOG = Logger.getLogger("com.lady.messenger.MyLogger");

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        LOG.info("\n********** Authorized admin on home page **********");
        this.mockMvc.perform(get("/"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='navbarSupportedContent']/ul/li/button").string("dev"));

        LOG.info("Result: PASS");
        LOG.info("********** ******************************* **********");
    }

    @Test
    public void testUpdateLogList() throws Exception {
        LOG.info("\n********** Update logs were loaded **********");

        this.mockMvc.perform(get("/"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='update-log-list']/div").nodeCount(4));

        LOG.info("Result: PASS");
        LOG.info("********** ************************* **********");
    }

    @Test
    public void testUpdateLogsFilter() throws Exception {
        LOG.info("\n********** Filter for update logs **********");

        this.mockMvc.perform(get("/").param("filter", "CAT1"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='update-log-list']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=3]").exists());

        LOG.info("Result: PASS");
        LOG.info("********** ************************ **********");
    }

    @Test
    public void testAddUpdateLog() throws Exception {
        LOG.info("\n********** Adding new update log **********");

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "123".getBytes());

        MockHttpServletRequestBuilder multipart = multipart("/")
                .file(file)
                .param("text", "NEW CAT CREATED TEXT")
                .param("title", "NEW CAT")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andExpect(authenticated());

        this.mockMvc.perform(get("/"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='update-log-list']/div").nodeCount(5))
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=10]").exists())
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=10]/div/div/div[2]/div/h5").string("NEW CAT"))
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=10]/div/div/div[2]/div/p").string("NEW CAT CREATED TEXT"));

        LOG.info("Result: PASS");
        LOG.info("********** *********************** **********");
    }
}
