package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * Contains details about the slots assigned including the technicians who are
 * assigned to a specific slot and the time in which the specific slot starts.
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
    private User userId;

    @Column
    private LocalDateTime slot;

    @OneToOne
    private SampleCollector sampleCollector;
}
