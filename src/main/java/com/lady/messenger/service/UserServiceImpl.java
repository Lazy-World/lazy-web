package com.lady.messenger.service;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.Message;
import com.lady.messenger.entity.Role;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.ChatRepository;
import com.lady.messenger.repository.UserRepository;
import com.lady.messenger.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @Transactional
    public Set<Message> getUserMessages(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getMessages();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean createUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        sendEmailToUser(user);

        return true;
    }

    @Override
    public boolean existsChatWithUsers(User user1, User user2) {
        boolean firstTry = chatRepository.existsChatByUser1AndUser2(user1, user2);
        boolean secondTry = chatRepository.existsChatByUser1AndUser2(user2, user1);

        return firstTry || secondTry;
    }

    @Override
    public Chat getChatWithUsers(User user1, User user2) {
        boolean firstTry = chatRepository.existsChatByUser1AndUser2(user1, user2);
        boolean secondTry = chatRepository.existsChatByUser1AndUser2(user2, user1);

        if (firstTry && secondTry) {
            throw new RuntimeException("Duplicate chat found");
        }

        if (firstTry) {
            return chatRepository.findChatByUser1AndUser2(user1, user2);
        }

        if (secondTry) {
            return chatRepository.findChatByUser1AndUser2(user2, user1);
        }

        return new Chat(user1, user2);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }

    @Override
    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if (StringUtils.hasLength(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (StringUtils.hasLength(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);

        if (isEmailChanged) {
            sendEmailToUser(user);
        }
    }

    @Override
    public void sendEmailToUser(User user) {
        if (!StringUtils.hasLength(user.getEmail())) {
            // TODO: Send exception to front-end
            return;
        }

        String message = String.format(
                "Hello, %s \n" +
                        "Welcome to Lazy World. \n\n" +
                        "Please, visit next link to activate your account: http://localhost:8081/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );

        mailSenderService.send(user.getEmail(), "Activation code", message);
    }
}
