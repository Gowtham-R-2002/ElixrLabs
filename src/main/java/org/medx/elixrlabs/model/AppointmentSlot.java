package org.medx.elixrlabs.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

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
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_slot")
    private LocalDate dateSlot;

    @Column(name = "time_slot")
    private String timeSlot;

    @Column
    @Enumerated(value = EnumType.STRING)
    private LocationEnum location;

    @OneToOne
    private SampleCollector sampleCollector;

    @Column(name = "appointment_place")
    @Enumerated(value = EnumType.STRING)
    private TestCollectionPlaceEnum testCollectionPlace;

    private boolean isSampleCollected;
}
