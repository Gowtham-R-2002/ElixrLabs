package org.medx.elixrlabs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.OtpDto;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.service.impl.UserService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    private LoginRequestDto loginRequestDto;
    private OtpDto otpDto;
    UserDetails userDetails;

    @BeforeEach
    void setUp() {
        loginRequestDto.setEmail("user@gmail.com");
        loginRequestDto.setPassword("user@123");
        otpDto = OtpDto.builder()
                .otp("123456")
                .build();
    }
}
