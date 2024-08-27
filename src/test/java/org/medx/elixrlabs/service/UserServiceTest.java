package org.medx.elixrlabs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.UserRepository;
import org.medx.elixrlabs.service.impl.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private String email;

    @BeforeEach
    void  setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .email("sabari@gmail.com")
                .password("sabari@123")
                .isDeleted(false)
                .UUID("1234-5678")
                .phoneNumber("1234567890")
                .build();

        email = "sabari@gmail.com";
    }

    @Test
    void testLoadUserByUsername_positive() {
        when(userRepository.findByEmailWithRoles(email)).thenReturn(user);
        User result = userRepository.findByEmailWithRoles(email);
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findByEmailWithRoles(email);
    }

    @Test
    void testLoadUserByUsername_negative() {
        when(userRepository.findByEmailWithRoles(email)).thenReturn(null);
        User result = userRepository.findByEmailWithRoles(email);
        assertNull(result);
        verify(userRepository).findByEmailWithRoles(email);
    }

    @Test
    void testLoadUserByUsername_exception() {
        when(userRepository.findByEmailWithRoles(email)).thenThrow(new UsernameNotFoundException("Database error"));
        assertThrows(UsernameNotFoundException.class, () -> userRepository.findByEmailWithRoles(email));
    }
}
