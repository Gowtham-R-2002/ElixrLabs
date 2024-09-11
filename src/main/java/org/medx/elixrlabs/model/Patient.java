package org.medx.elixrlabs.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class holds the data related to an Patient including their list of orders
 * and other relevant details.
 * The {@code Patient} class represents a patient whom a user who is receiving medical care,
 * especially when they are ill or injured and need treatment.
 * </p>
 *
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @Column
    @SequenceGenerator(name = "PatientIdGenerator", initialValue = 10000)
    @GeneratedValue(generator = "PatientIdGenerator", strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Order> orders;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
