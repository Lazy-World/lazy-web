package com.lady.messenger.service.interfaces;

import com.lady.messenger.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAll();

    boolean createUser(User user);

    boolean activateUser(String code);

    void saveUser(User user, String username, Map<String, String> form);

    void updateUserInfo(User user, String password, String email);

    void sendEmailToUser(User user);
}
