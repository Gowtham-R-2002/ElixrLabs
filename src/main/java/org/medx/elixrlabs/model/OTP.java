package org.medx.elixrlabs.model;

import java.util.Calendar;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * This class holds the data related to an user including their otp, calender.
 * The {@code OTP} class represents a One-time password(OTP) systems provide a mechanism
 * for logging on to a network or service using a unique password that can only be used once,
 * as the name suggests.
 * </p>
 *
 * @version  1.0
 */
@Data
@Builder
public class OTP {
    private String otp;
    private Calendar calendar;
}
