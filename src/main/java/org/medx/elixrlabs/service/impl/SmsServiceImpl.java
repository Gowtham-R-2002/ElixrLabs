package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

@Service
public class SmsServiceImpl implements SmsService {
    public static final String ACCOUNT_SID = "AC290dbd75918c163b32d0604dfaf15a32";
    public static final String AUTH_TOKEN = "fc0eb66854b5812f792b89570878fa14";
    @Override
    public OTP sendSmsAndGetOtp(String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        int otp = new Random().nextInt(900000) + 100000;
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+91" + phoneNumber),
                        new com.twilio.type.PhoneNumber("+17603001687"),
                        "ElixrLabs : Your OTP for login is " + otp + ". Valid for 5 minutes.")
                .create();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        calendar.add(Calendar.MINUTE, 5);
        return OTP.builder()
                .otp(String.valueOf(otp))
                .calendar(calendar)
                .build();
    }
}
