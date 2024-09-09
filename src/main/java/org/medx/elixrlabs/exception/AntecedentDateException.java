package org.medx.elixrlabs.exception;

/**
 * <p>Exception for handling errors related to antecedent dates.
 * This exception is thrown when a date related validation fails, such as
 * when a date is found to be in the past. </p>
 *
 * @author Gowtham R
 */
public class AntecedentDateException extends RuntimeException{
    public AntecedentDateException(String message) {
        super(message);
    }
}
