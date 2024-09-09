package org.medx.elixrlabs.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.OtpDto;
import org.medx.elixrlabs.exception.OTPValidationException;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * <p>
 * This {@code AuthenticationService} contains business logic for handling Authentication related
 * operations. It acts as a bridge between the controller layer and the repository
 * layer, ensuring that business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    private Authentication userAuthentication;
    private OTP otp;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    public void createAuthentication(LoginRequestDto loginRequestDto) throws MessagingException, IOException {
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

    public String generateToken(OtpDto otpDto) {
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
