package org.medx.elixrlabs.exception;

/**
 * <p>Custom exception class for handling errors related to slot operations
 * This exception is thrown when a slot related error occurs like invalid slot
 * and unavailability of slots</p>
 *
 * @author Sabarinathan K
 */
public class SlotException extends RuntimeException {
    public SlotException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlotException(String message) {
        super(message);
    }
}
