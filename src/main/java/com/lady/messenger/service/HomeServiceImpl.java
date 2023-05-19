package com.lady.messenger.service;

import com.lady.messenger.entity.UpdateLog;
import com.lady.messenger.repository.UpdateLogRepository;
import com.lady.messenger.service.interfaces.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

@Service(value = "HomeService")
public class HomeServiceImpl implements HomeService {
    private final UpdateLogRepository updateLogRepository;

    public HomeServiceImpl(UpdateLogRepository updateLogRepository) {
        this.updateLogRepository = updateLogRepository;
    }

    public boolean isFieldEmpty(String field) {
        return (field == null || field.isEmpty());
    }

    public boolean isFileValid(MultipartFile file) {
        return file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty();
    }

    public Iterable<UpdateLog> reverseUpdateLogList(Iterable<UpdateLog> updateLog) {
        LinkedList<UpdateLog> list = new LinkedList<>();
        for (UpdateLog ul : updateLog) {
            list.addFirst(ul);
        }
        return list;
    }

    public Iterable<UpdateLog> getUpdateLogsWithFilter(String searchField) {
        return isFieldEmpty(searchField)
                ? updateLogRepository.findAll()
                : updateLogRepository.findByTitle(searchField);
    }

    public Iterable<UpdateLog> getAllUpdateLogs() {
        return updateLogRepository.findAll();
    }

    public UpdateLog getUpdateLogById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException ("Invalid id was received");
        }
        return updateLogRepository.findUpdateLogById(id);
    }

    public String getFilenameWithUUID(MultipartFile file) {
        String uuidFile = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        return uuidFile + "." + originalFilename;
    }

    public void uploadFile(@Valid UpdateLog updateLog, String uploadPath, MultipartFile file) throws IOException {
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
