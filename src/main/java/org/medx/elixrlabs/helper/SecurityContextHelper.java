package org.medx.elixrlabs.helper;

import org.medx.elixrlabs.exception.LabException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHelper {
    public static String extractEmailFromContext() {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser != null) {
            return currentUser.toString();
        } else {
            throw new LabException("Unable to decode security context");
        }
    }
}
