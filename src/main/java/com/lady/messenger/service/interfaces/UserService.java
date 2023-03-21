package com.lady.messenger.service.interfaces;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.Message;
import com.lady.messenger.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    List<User> findAll();

    Chat getChatWithUsers(User user1, User user2);

    boolean existsChatWithUsers(User user1, User user2);

    boolean createUser(User user);

    boolean activateUser(String code);

    void saveUser(User user, String username, Map<String, String> form);

    void updateUserInfo(User user, String password, String email);

    void sendEmailToUser(User user);
}
