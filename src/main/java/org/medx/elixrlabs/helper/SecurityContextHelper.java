package org.medx.elixrlabs.helper;

import org.medx.elixrlabs.exception.LabException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Helper class for managing security context operations in the application.This
 * class provides methods to interact with security context.
 */
public class SecurityContextHelper {

    /**
     * Extracts the email address of the currently authenticated user from the
     * security context
     *
     * @return email address of the authenticated user
     */
    public static String extractEmailFromContext() {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser != null) {
            return currentUser.toString();
        } else {
            throw new LabException("Unable to decode security context");
        }
    }
}
