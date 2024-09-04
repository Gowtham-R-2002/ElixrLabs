package org.medx.elixrlabs.service.impl;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.OtpDto;
import org.medx.elixrlabs.exception.OTPValidationException;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.service.impl.AuthenticationService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.service.impl.UserService;
import org.medx.elixrlabs.util.LocationEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmailService emailService;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication userAuthentication;

    @Mock
    private OTP otp;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testCreateAuthentication_Authenticated() throws MessagingException, IOException {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("password")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(emailService.sendMailAndGetOtp(loginRequestDto.getEmail())).thenReturn(otp);

        authenticationService.createAuthentication(loginRequestDto);

        verify(emailService, times(1)).sendMailAndGetOtp(loginRequestDto.getEmail());
    }

    @Test
    void testCreateAuthentication_NotAuthenticated() throws MessagingException, IOException {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("password")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        authenticationService.createAuthentication(loginRequestDto);

        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
        verify(emailService, times(0)).sendMailAndGetOtp(loginRequestDto.getEmail());
    }

    @Test
    void testGenerateToken_ValidOtp() {
        OtpDto otpDto = new OtpDto();
        otpDto.setOtp("123456");

        Calendar validCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        validCalendar.add(Calendar.MINUTE, 5);

        when(otp.getOtp()).thenReturn("123456");
        when(otp.getCalendar()).thenReturn(validCalendar);

        UserDetails userDetails = mock(UserDetails.class);
        when(userAuthentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        LocationEnum location = LocationEnum.MARINA; // Replace with actual enum value
        when(userService.loadUserByUsername("testUser")).thenReturn(User.builder().place(LocationEnum.MARINA).build());
        when(jwtService.generateToken(userDetails, location)).thenReturn("mockToken");

        String token = authenticationService.generateToken(otpDto);

        assertEquals("mockToken", token);
    }

    @Test
    void testGenerateToken_InvalidOtp() {
        OtpDto otpDto = new OtpDto();
        otpDto.setOtp("123456");

        when(otp.getOtp()).thenReturn("654321");

        assertThrows(OTPValidationException.class, () -> authenticationService.generateToken(otpDto));
    }

    @Test
    void testGenerateToken_ExpiredOtp() {
        OtpDto otpDto = new OtpDto();
        otpDto.setOtp("123456");

        Calendar expiredCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        expiredCalendar.add(Calendar.MINUTE, -5);

        when(otp.getOtp()).thenReturn("123456");
        when(otp.getCalendar()).thenReturn(expiredCalendar);

        assertThrows(OTPValidationException.class, () -> authenticationService.generateToken(otpDto));
    }
}
