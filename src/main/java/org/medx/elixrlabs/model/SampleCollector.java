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

@Builder
@Data
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SampleCollector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "boolean default true")
    private boolean isVerified;
}
