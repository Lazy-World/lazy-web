package com.lady.messenger.service;

import com.lady.messenger.entity.Message;
import com.lady.messenger.entity.UpdateLog;
import com.lady.messenger.repository.UpdateLogRepository;
import com.lady.messenger.service.interfaces.HomeService;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

@Service(value = "HomeService")
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UpdateLogRepository updateLogRepository;

    public boolean isFieldEmpty(String field) {
        return (field == null || field.isEmpty());
    }

    public boolean isFileValid(MultipartFile file) {
        return file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty();
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

    public UpdateLog getUpdateLogById(Long id) {
        return id != null && id > 0
                ? updateLogRepository.findUpdateLogById(id)
                : null;
    }

    public String getFilenameWithUUID(MultipartFile file) {
        String uuidFile = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        return uuidFile + "." + originalFilename;
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
}
