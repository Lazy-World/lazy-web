package com.lady.messenger.service;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.Role;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.ChatRepository;
import com.lady.messenger.repository.UserRepository;
import com.lady.messenger.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final MailSenderService mailSenderService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    public UserServiceImpl(MailSenderService mailSenderService, PasswordEncoder passwordEncoder, UserRepository userRepository, ChatRepository chatRepository) {
        this.mailSenderService = mailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return user;
    }

    public boolean isLoginCorrect(String login) {
        String regex = "^[A-Za-z0-9_-]{3,10}$";
        return login.matches(regex);
    }

    public boolean isPasswordCorrect(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9_-]{4,16}$";
        return password.matches(regex);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

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

    public boolean existsChatWithUsers(List<User> users) {
        return chatRepository.existsByUsers(users, (long) users.size(), (long) new HashSet<>(users).size());
    }

    public Chat getChatWithUsers(List<User> users) {
        if (existsChatWithUsers(users))
            return chatRepository.findByUsers(users, (long) users.size(), (long) new HashSet<>(users).size());

        return new Chat(users);
    }

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

    public void sendEmailToUser(User user) {
        if (!StringUtils.hasLength(user.getEmail())) {
            return;
        }

        String message = String.format(
                "Приветствую, %s!\n \n" +

                "Добро пожаловать в Lazy World.\n" +
                "Для подтверждения почты перейдите по ссылке:\n \n" +

                "http://localhost:3000/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );

        mailSenderService.send(user.getEmail(), "Активация аккаунта", message);
    }
}
