package org.medx.elixrlabs.exception;

public class OTPValidationException extends RuntimeException{
    public OTPValidationException(String message) {
        super(message);
    }
}
