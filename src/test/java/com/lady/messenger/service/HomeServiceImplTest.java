package com.lady.messenger.service;

import com.lady.messenger.entity.UpdateLog;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.UpdateLogRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class HomeServiceImplTest {
    @Mock
    private UpdateLogRepository updateLogRepository;

    @InjectMocks
    private HomeServiceImpl homeService;

    @Test
    public void testIsFieldEmpty() {
        assertTrue(homeService.isFieldEmpty(null), "Null was provided");
        assertTrue(homeService.isFieldEmpty(""), "Empty string was provided");
        assertFalse(homeService.isFieldEmpty("cat"), "Filled string was provided");
    }

    @Test
    public void testIsFileValid() {
        MockMultipartFile correctFile = new MockMultipartFile("file", "test.txt", "text/plain", "123".getBytes());
        MockMultipartFile fileWithEmptyName = new MockMultipartFile("file", "", "text/plain", "123".getBytes());

        assertTrue(homeService.isFileValid(correctFile));
        assertFalse(homeService.isFileValid(null));
        assertFalse(homeService.isFileValid(fileWithEmptyName));
    }

    @Test
    public void testReverseUpdateLogList() {
        User testUser = new User();

        List<UpdateLog> startList = new ArrayList<>();
        startList.add(new UpdateLog(1L, "cat1", "textCat1", testUser));
        startList.add(new UpdateLog(2L, "cat2", "textCat2", testUser));

        List<UpdateLog> reversedList = new ArrayList<>();
        reversedList.add(new UpdateLog(2L, "cat2", "textCat2", testUser));
        reversedList.add(new UpdateLog(1L, "cat1", "textCat1", testUser));

        assertEquals(reversedList, homeService.reverseUpdateLogList(startList), "message");
    }

    @Test
    public void testGetUpdateLogsWithFilter() {
        User testUser = new User();

        UpdateLog updateLog1 = new UpdateLog(1L, "cat1", "textCat1", testUser);
        UpdateLog updateLog2 = new UpdateLog(2L, "cat2", "textCat2", testUser);
        UpdateLog updateLog3 = new UpdateLog(3L, "cat1", "textCat3", testUser);

        List<UpdateLog> emptyFilterResult = List.of(updateLog1, updateLog2, updateLog3);
        List<UpdateLog> cat1FilterResult = List.of(updateLog1, updateLog3);
        List<UpdateLog> cat2FilterResult = List.of(updateLog2);

        when(updateLogRepository.findAll()).thenReturn(emptyFilterResult);
        when(updateLogRepository.findByTitle("cat1")).thenReturn(cat1FilterResult);
        when(updateLogRepository.findByTitle("cat2")).thenReturn(cat2FilterResult);

        assertEquals(cat1FilterResult, homeService.getUpdateLogsWithFilter("cat1"), "Search works wrong");
        assertEquals(cat2FilterResult, homeService.getUpdateLogsWithFilter("cat2"), "Search works wrong");
        assertEquals(emptyFilterResult, homeService.getUpdateLogsWithFilter(""), "Search works wrong");
    }

    @Test
    public void testGetAllUpdateLogs() {
        User testUser = new User();

        UpdateLog updateLog1 = new UpdateLog(1L, "cat1", "textCat1", testUser);
        UpdateLog updateLog2 = new UpdateLog(2L, "cat2", "textCat2", testUser);

        List<UpdateLog> allUpdateLogs = List.of(updateLog1, updateLog2);

        when(updateLogRepository.findAll()).thenReturn(List.of(updateLog1, updateLog2));
        assertEquals(allUpdateLogs, homeService.getAllUpdateLogs(), "Not able to get all update logs");
    }

    @Test
    public void testGetUpdateLogById() {
        final Long updateLogID_0 = 0L;
        assertThrows(IllegalArgumentException.class,() -> homeService.getUpdateLogById(updateLogID_0));

        final Long updateLogID_1 = 1L;
        User testUser = new User();
        UpdateLog updateLog1 = new UpdateLog(updateLogID_1, "cat1", "textCat1", testUser);

        when(updateLogRepository.findUpdateLogById(updateLogID_1)).thenReturn(updateLog1);
        assertEquals(updateLog1, homeService.getUpdateLogById(updateLogID_1), "Incorrect update log search");
    }

    @Test
    public void testGetFilenameWithUUID() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.txt");

        String filename = homeService.getFilenameWithUUID(file);
        assertTrue(filename.endsWith(".test.txt"));
        assertTrue(filename.matches("^[a-f0-9\\-]+\\.test\\.txt$"));
    }

    @Test
    void testUploadFile() throws IOException {
        UpdateLog updateLog = new UpdateLog();
        String uploadPath   = "testUploadPath";
        File uploadDir      = new File(uploadPath);
        MultipartFile file  = mock(MultipartFile.class);

        when(file.getOriginalFilename()).thenReturn("testFile.txt");
        homeService.uploadFile(updateLog, uploadPath, file);

        String filename = updateLog.getFilename();
        assertTrue(filename.matches("[a-f0-9\\-]+\\.testFile\\.txt"));
        assertTrue(uploadDir.exists());

        if (uploadDir.exists()) {
            FileUtils.deleteDirectory(uploadDir);
        }
    }

}
