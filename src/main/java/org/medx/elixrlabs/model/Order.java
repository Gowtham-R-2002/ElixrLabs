package org.medx.elixrlabs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.PaymentStatusEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TestStatusEnum;

import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * This class holds the data related to an user including their id, testStatus, sampleCollectionPlace,
 * paymentStatus, labLocation, homeLocation and other relevant details.
 * The {@code Order} class represents a customer order for one or
 * more lab tests or test packages. This class captures the details
 * of the order, including the associated user, test status, payment
 * status, and location information for sample collection. It also
 * maintains a relationship with the test package and appointment slot.
 * </p>
 *
 * @author  Sabarinathan K
 * @version  1.0
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Patient patient;

    @Column(name = "test_status")
    @Enumerated(value = EnumType.STRING)
    private TestStatusEnum testStatus;

    @Column(name = "sample_collection_place")
    @Enumerated(value = EnumType.STRING)
    private TestCollectionPlaceEnum sampleCollectionPlace;

    @Column(name = "payment_status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Column(name = "lab_location")
    @Enumerated(value = EnumType.STRING)
    private LocationEnum labLocation;

    @OneToOne
    private TestPackage testPackage;

    @ManyToMany
    @JoinTable(name = "order_test",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<LabTest> tests;

    @OneToOne
    private AppointmentSlot slot;

    @OneToOne
    private TestResult testResult;

    private double price;

    private Calendar orderDateTime;
}
