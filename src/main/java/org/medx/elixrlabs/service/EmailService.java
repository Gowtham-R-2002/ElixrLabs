package org.medx.elixrlabs.service;

import jakarta.mail.MessagingException;
import org.medx.elixrlabs.model.OTP;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface EmailService {
    OTP sendMailAndGetOtp(String email) throws MessagingException, IOException;
}