package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * <p>
 * It represents the users who are associated with the role sample collector.
 * Contains extra details that could relate to a sample collectors like their
 * staff id and name.
 * </p>
 *
 * @author Deolin Jaffens
 * @version 1.0
 */

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SampleCollector extends User {

    @Column(columnDefinition = "boolean default true")
    private boolean isVerified;
}
