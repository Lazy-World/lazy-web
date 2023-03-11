package com.lady.messenger.repository;

import com.lady.messenger.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
