package com.lady.messenger.service;

import com.lady.messenger.entity.Role;
import com.lady.messenger.entity.User;
import com.lady.messenger.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() {
        User user = new User("test_user");
        user.setPassword("password");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        boolean result = userService.createUser(user);

        assertTrue(result);
        assertFalse(user.isActive());
        assertEquals(Collections.singleton(Role.USER), user.getRoles());
        assertNotNull(user.getActivationCode());
        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository).save(user);
    }
}
