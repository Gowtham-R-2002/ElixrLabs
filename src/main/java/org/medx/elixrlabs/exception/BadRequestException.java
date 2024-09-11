package org.medx.elixrlabs.exception;

/**
 * <p>
 * Custom exception class for handling errors where the bad request occurs
 * This exception is thrown when user input is correct but it fails to satisfy
 * given condition.
 * </p>
 *
 * @author Sabarinathan K
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
