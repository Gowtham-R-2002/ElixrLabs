package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.OTP;
import org.springframework.stereotype.Service;

@Service
public interface SmsService {
    OTP sendSmsAndGetOtp(String phoneNumber);
}