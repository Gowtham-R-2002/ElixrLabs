package org.medx.elixrlabs.audit;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

/**
 * <p>
 * The SpringSecurityAuditorAware class implements {@link AuditorAware} to provide
 * the current auditor (user) for JPA auditing. It integrates with Spring Security
 * to fetch the username of the currently authenticated user, which is then used
 * to automatically populate audit fields such as createdBy and modifiedBy.
 * This class is particularly useful in applications that utilize Spring Security,
 * ensuring that audit information is accurately captured based on the current user session.
 * </p>
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    /**
     * Retrieves the current auditor (i.e., the username of the authenticated user)
     * from the Spring Security context.
     *
     * @return an {@link Optional} containing the username of the authenticated user,
     *         or an empty {@link Optional} if the user is not authenticated or is anonymous.
     */
    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        return Optional.of(authentication.getName());
    }
}
