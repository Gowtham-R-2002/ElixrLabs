package org.medx.elixrlabs.model;

import java.util.Calendar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTP {
    private String otp;
    private Calendar calendar;
}
