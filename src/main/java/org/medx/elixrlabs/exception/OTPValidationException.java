package org.medx.elixrlabs.exception;

/**
 * <p>
 * Custom exception class for handling OTP validation errors.This exception is
 * thrown when an OTP validation process fails.
 * </p>
 */

public class OTPValidationException extends RuntimeException{
    public OTPValidationException(String message) {
        super(message);
    }
}
