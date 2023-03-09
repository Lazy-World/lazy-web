package com.lady.messenger.service;

import com.lady.messenger.entity.UpdateLog;
import com.lady.messenger.entity.dto.CaptchaResponseDto;
import com.lady.messenger.repository.UpdateLogRepository;
import com.lady.messenger.service.interfaces.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

@Service
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    private UpdateLogRepository updateLogRepository;

    @Autowired
    private RestTemplate restTemplate;

    public String getFilenameWithUUID(MultipartFile file) {
        String uuidFile = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        return uuidFile + "." + originalFilename;
    }

    public boolean isFieldEmpty(String field) {
        return (field == null || field.isEmpty());
    }

    public boolean isFileValid(MultipartFile file) {
        return file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty();
    }

    public boolean isCaptchaValid(String captchaUrl) {
        CaptchaResponseDto captchaResponse = restTemplate.postForObject(captchaUrl, Collections.emptyList(), CaptchaResponseDto.class);
        return captchaResponse != null && captchaResponse.isSuccess();
    }

    public boolean hasChanged(String curValue, String newValue) {
        return curValue != null && !curValue.equals(newValue);
    }

    // TODO: should return errors / error codes
    public void uploadFile(UpdateLog updateLog, String uploadPath, MultipartFile file) throws IOException {
        if (!isFileValid(file)) {
            return;
        }

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String resultFilename = getFilenameWithUUID(file);

        file.transferTo(new File(uploadPath + "/" + resultFilename));
        updateLog.setFilename(resultFilename);
    }

    // TODO: make a Template method instead
    public Iterable<UpdateLog> reverseUpdateLog(Iterable<UpdateLog> updateLog) {
        LinkedList<UpdateLog> list = new LinkedList<>();
        for (UpdateLog ul : updateLog) {
            list.addFirst(ul);
        }
        return list;
    }

    public Iterable<UpdateLog> getUpdateLogs(String searchField) {
        Iterable<UpdateLog> updateLogs = isFieldEmpty(searchField)
                ? updateLogRepository.findAll()
                : updateLogRepository.findByTag(searchField);

        return reverseUpdateLog(updateLogs);
    }
}
