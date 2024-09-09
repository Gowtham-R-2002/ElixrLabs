package org.medx.elixrlabs.controller;

import java.io.IOException;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.medx.elixrlabs.service.impl.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.OtpDto;

/**
 * <p>Handles user authentication, login processes, and OTP management.
 * Manages authentication attempts, OTP validation, and token generation
 * for users upon successful authentication.</p>
 *
 * @author Gowtham R
 */

@RestController
@RequestMapping("api/v1/auth/login")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationService authenticationService;


    /**
     * <p>Authenticates user credentials and initiates OTP sending process
     * if authentication is successful.</p>
     *
     * @param loginRequestDto Contains email and password for authentication.
     * @throws MessagingException If an error occurs while sending the email.
     * @throws IOException If an I/O error occurs during email sending.
     */

    @PostMapping
    public void login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws MessagingException, IOException {
        authenticationService.createAuthentication(loginRequestDto);
    }

    /**
     * <p>Validates the provided OTP and generates a JWT token if the OTP
     * is correct and has not expired. Updates the security context for the user.</p>
     *
     * @param otpDto Contains the OTP for verification.
     * @return A JWT token if the OTP is valid.
     */

    @PostMapping("verify")
    public String verifyAndGenerateToken(@Valid @RequestBody OtpDto otpDto) {
        return authenticationService.generateToken(otpDto);
    }
}
