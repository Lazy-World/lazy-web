package com.lady.messenger.repository;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    boolean existsChatByUser1AndUser2(User user1, User user2);

    Chat findChatByUser1AndUser2(User user1, User user2);
}
