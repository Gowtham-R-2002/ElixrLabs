package org.medx.elixrlabs.exception;

/**
 * <p>
 * Custom exception for handling error that occurs while accessing the database.
 * This exception is thrown when there is a issue in accessing the database.
 * </p>
 */

public class LabException extends RuntimeException{
    public LabException(String message, Throwable cause) {
        super(message, cause);
    }

    public LabException(String message) {
        super(message);
    }
}
