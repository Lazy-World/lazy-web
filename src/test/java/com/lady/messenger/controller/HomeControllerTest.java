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

import java.io.File;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("dev")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql-before-test/create-user.sql", "/sql-before-test/update-log.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql-after-test/update-log.sql", "/sql-after-test/create-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='navbarSupportedContent']/ul/li/button").string("dev"));
    }

    @Test
    public void testUpdateLogList() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='update-log-list']/div").nodeCount(4));
    }

    @Test
    public void testUpdateLogsFilter() throws Exception {
        this.mockMvc.perform(get("/").param("filter", "CAT1"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='update-log-list']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=3]").exists());
    }

    @Test
    public void testAddUpdateLogByAdmin() throws Exception {
        MockMultipartFile fileForUpdateLog = new MockMultipartFile("file", "unsafe_test.txt", "text/plain", "123".getBytes());

        MockHttpServletRequestBuilder multipart = multipart("/")
                .file(fileForUpdateLog)
                .param("text", "NEW CAT CREATED TEXT")
                .param("title", "NEW CAT")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andExpect(authenticated());

        this.mockMvc.perform(get("/"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='update-log-list']/div").nodeCount(5))
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=10]").exists())
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=10]/div/div/div[2]/div/p").string("NEW CAT CREATED TEXT"))
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=10]/div/div/div[2]/div/h5").string("NEW CAT"));

        File dir = new File("uploads");
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.getName().matches("^[a-f0-9\\-]+\\.unsafe_test\\.txt$")) {
                file.delete();
            }
        }
    }

    @Test
    public void testEditExistingUpdateLog() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/edit")
                    .param("log", "1")
                    .param("text", "FIRST TEXT (Edited)")
                    .param("title", "CAT1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andExpect(authenticated());

        this.mockMvc.perform(get("/"))
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=1]/div/div/div[2]/div/p").string("FIRST TEXT (Edited)"))
                .andExpect(xpath("//div[@id='update-log-list']/div[@data-id=1]/div/div/div[2]/div/h5").string("CAT1"));
    }
}
