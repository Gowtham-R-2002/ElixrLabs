package org.medx.elixrlabs.model;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class OTP {
    private String otp;
    private Calendar calendar;
}
