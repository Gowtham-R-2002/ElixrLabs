package org.medx.elixrlabs.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import jakarta.mail.MessagingException;
import org.medx.elixrlabs.service.impl.SampleCollectorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.OtpDto;
import org.medx.elixrlabs.exception.OTPValidationException;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.service.impl.UserService;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * REST controller for managing Authentication-related operations.
 *
 * <p>
 * This controller handles HTTP requests and provides endpoints for
 * login, verifies OTP and returns generated token.
 * It is annotated with Spring MVC annotations to define the URL mappings
 * and request handling logic.
 * </p>
 */
@RestController
@RequestMapping("api/v1/auth/login")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(SampleCollectorServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    private Authentication userAuthentication;
    private OTP otp;

    @PostMapping
    public void login(@RequestBody LoginRequestDto loginRequestDto) throws MessagingException, IOException {
        logger.debug("Attempting to login into the application for email: {}", loginRequestDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        userAuthentication = authentication;
        if(authentication.isAuthenticated()) {
            logger.debug("Sending OTP for email: {}", loginRequestDto.getEmail());
            otp = emailService.sendMailAndGetOtp(loginRequestDto.getEmail());
        } else {
            logger.warn("Error occurred while  by email: {}", loginRequestDto.getEmail());
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        }
    }

    @PostMapping("verify")
    public String verifyAndGenerateToken(@RequestBody OtpDto otpDto) {
        logger.debug("Verifying OTP and Generating token");
        if (otpDto.getOtp().equals(otp.getOtp()) &&
                ((Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"))).compareTo(otp.getCalendar()) < 0)) {
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            logger.info("Successfully verified user!");
        } else {
            if (!otpDto.getOtp().equals(otp.getOtp())) {
                logger.warn("Entered invalid OTP");
                throw new OTPValidationException("Invalid OTP Entered !");
            } else {
                logger.warn("Entered OTP got expired");
                throw new OTPValidationException("OTP Expired ! Please Re-login");
            }
        }
        UserDetails userDetails = (UserDetails) userAuthentication.getPrincipal();
        LocationEnum address = userService.loadUserByUsername(userDetails.getUsername()).getPlace();
        return jwtService.generateToken(userDetails, address);
    }
}
