package org.medx.elixrlabs.exception;

import javax.naming.AuthenticationException;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause){
        super(message);
    }
}
