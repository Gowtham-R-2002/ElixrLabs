package org.medx.elixrlabs.exception;

/**
 * <p>
 * Exception class for handling slot related exceptions.
 * </p>
 */
public class SlotException extends RuntimeException{
    public SlotException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlotException(String message) {
        super(message);
    }
}
