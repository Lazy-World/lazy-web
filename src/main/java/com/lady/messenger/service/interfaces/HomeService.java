package com.lady.messenger.service.interfaces;

import com.lady.messenger.entity.UpdateLog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface HomeService {
    boolean isFieldEmpty(String field);

    boolean isFileValid(MultipartFile file);

    Iterable<UpdateLog> reverseUpdateLog(Iterable<UpdateLog> updateLog);

    Iterable<UpdateLog> getUpdateLogs(String searchField);

    String getFilenameWithUUID(MultipartFile file);

    void uploadFile(UpdateLog updateLog, String uploadPath, MultipartFile file) throws IOException;
}
