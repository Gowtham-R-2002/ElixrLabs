package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

import java.time.LocalDate;

/**
 * <p>
 * The {@code AppointmentSlot} class represents a scheduled time
 * slot for sample collection or test appointment. Each slot is
 * associated with a sample collector and is linked to an order to ensure
 * that the sample collection happens at the right time.
 * </p>
 *
 * @author Deolin Jaffens
 * @version 1.0
 */

@Builder
@Entity
@Data
@Table(name = "appointment_slots")
public class AppointmentSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "date_slot")
    private LocalDate dateSlot;

    @Column(name = "time_slot")
    private String timeSlot;

    @OneToOne
    private SampleCollector sampleCollector;

    @Column(name = "appointment_place")
    @Enumerated(value = EnumType.STRING)
    private TestCollectionPlaceEnum appointmentPlace;

    private boolean isSampleCollected;
}
