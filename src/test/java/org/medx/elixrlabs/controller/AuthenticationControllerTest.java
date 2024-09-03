package org.medx.elixrlabs.controller;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.OtpDto;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.service.impl.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    @InjectMocks
    private AuthenticationController authenticationController;

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
    private UserDetails userDetails;
    private OTP otp;

    @BeforeEach
    void setUp() {
        loginRequestDto = LoginRequestDto.builder()
                .email("user@gmail.com")
                .password("user@123")
                .build();
        otpDto = OtpDto.builder()
                .otp("123456")
                .build();
    }

//    @Test
//    public void testLogin() throws MessagingException, IOException {
//        when(emailService.sendMailAndGetOtp(loginRequestDto.getEmail())).thenReturn(otp);
//        authenticationController.login(loginRequestDto);
//    }

}
