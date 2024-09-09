package org.medx.elixrlabs.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <p>
 * The AuditingConfig class configures JPA auditing for the application.
 * It enables automatic population of audit-related fields (such as createdBy and modifiedBy)
 * in entities that extend the Auditable class or contain audit fields.
 * </p>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
