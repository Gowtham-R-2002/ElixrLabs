package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
                .isBlocked(false)
                .UUID("1234-5678")
                .phoneNumber("1234567890")
                .build();

        email = "sabari@gmail.com";
    }

    @Test
    void testLoadUserByUsername_positive() {
        when(userRepository.findByEmailWithRole(email)).thenReturn(user);
        User foundUser = userService.loadUserByUsername(email);
        assertEquals(foundUser.getEmail(),email);
        verify(userRepository).findByEmailWithRole(email);
    }

    @Test
    void testLoadUserByUsername_negative() {
        when(userRepository.findByEmailWithRole(email)).thenThrow(LabException.class);
        assertThrows(LabException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    void testLoadUserByUsername_exception() {
        when(userRepository.findByEmailWithRole(email)).thenReturn(null);
        assertThrows(LabException.class, () -> userService.loadUserByUsername(email));
    }
}
