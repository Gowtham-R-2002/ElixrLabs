package org.medx.elixrlabs.controller;

import jakarta.mail.MessagingException;
import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.OtpDto;
import org.medx.elixrlabs.exception.OTPValidationException;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.service.impl.UserService;
import org.medx.elixrlabs.util.LocationEnum;
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

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

@RestController
@RequestMapping("api/v1/auth/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    private Authentication userAuthentication;
    private boolean isverified;
    private OTP otp;

    @PostMapping
    public void login(@RequestBody LoginRequestDto loginRequestDto) throws MessagingException, IOException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        userAuthentication = authentication;
        if(authentication.isAuthenticated()) {
            otp = emailService.sendMailAndGetOtp(loginRequestDto.getEmail());
        } else {
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        }
    }

    @PostMapping("verify")
    public String verifyAndGenerateToken(@RequestBody OtpDto otpDto) {
        if (otpDto.getOtp().equals(otp.getOtp()) &&
                ((Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"))).compareTo(otp.getCalendar()) < 0)) {
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        } else {
            if (!otpDto.getOtp().equals(otp.getOtp())) {
                throw new OTPValidationException("Invalid OTP Entered !");
            } else {
                throw new OTPValidationException("OTP Expired ! Please Re-login");
            }
        }
        UserDetails userDetails = (UserDetails) userAuthentication.getPrincipal();
        LocationEnum address = userService.loadUserByUsername(userDetails.getUsername()).getPlace();
        return jwtService.generateToken(userDetails, address);
    }
}
