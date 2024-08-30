package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

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
@Table(name = "sample_collectors")
public class SampleCollector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<AppointmentSlot> appointmentSlots;

    @Column(columnDefinition = "boolean default true")
    private boolean isVerified;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private boolean isDeleted;
}
