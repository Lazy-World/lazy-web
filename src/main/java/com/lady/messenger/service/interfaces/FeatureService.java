package com.lady.messenger.service.interfaces;

import com.lady.messenger.entity.UpdateLog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FeatureService {
    boolean isFieldEmpty(String field);

    boolean isFileValid(MultipartFile file);

    boolean isCaptchaValid(String captchaUrl);

    boolean hasChanged(String curValue, String newValue);

    String getFilenameWithUUID(MultipartFile file);

    void uploadFile(UpdateLog updateLog, String uploadPath, MultipartFile file) throws IOException;

    Iterable<UpdateLog> reverseUpdateLog(Iterable<UpdateLog> updateLog);

    Iterable<UpdateLog> getUpdateLogs(String searchField);
}
