package org.medx.elixrlabs.exception;

public class SlotException extends RuntimeException{
    public SlotException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlotException(String message) {
        super(message);
    }
}
