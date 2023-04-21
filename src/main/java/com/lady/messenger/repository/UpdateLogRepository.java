package com.lady.messenger.repository;

import com.lady.messenger.entity.UpdateLog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UpdateLogRepository extends CrudRepository<UpdateLog, Long> {
    List<UpdateLog> findByTitle(String tag);

    UpdateLog findUpdateLogById(Long id);
}
