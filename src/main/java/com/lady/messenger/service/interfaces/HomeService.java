package com.lady.messenger.service.interfaces;

import com.lady.messenger.entity.UpdateLog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface HomeService {
    boolean isFieldEmpty(String field);

    boolean isFileValid(MultipartFile file);

    Iterable<UpdateLog> reverseUpdateLogList(Iterable<UpdateLog> updateLog);

    Iterable<UpdateLog> getUpdateLogsWithFilter(String searchField);

    Iterable<UpdateLog> getAllUpdateLogs();

    UpdateLog getUpdateLogById(Long id);

    String getFilenameWithUUID(MultipartFile file);

    void uploadFile(UpdateLog updateLog, String uploadPath, MultipartFile file) throws IOException;
}
