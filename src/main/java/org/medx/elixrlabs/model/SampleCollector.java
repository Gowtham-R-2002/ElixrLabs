package org.medx.elixrlabs.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "appointment_slots_id")
    private List<AppointmentSlot> appointmentSlots;

    @Column(columnDefinition = "boolean default true")
    private boolean isVerified;
}
