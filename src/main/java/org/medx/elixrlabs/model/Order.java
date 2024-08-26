package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medx.elixrlabs.util.AddressEnum;
import org.medx.elixrlabs.util.PaymentStatusEnum;
import org.medx.elixrlabs.util.TestStatusEnum;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private User user;

    @Column(name = "test_status")
    @Enumerated(value = EnumType.STRING)
    private TestStatusEnum testStatus;

    @Column(name = "sample_collection_place")
    @Enumerated(value = EnumType.STRING)
    private AddressEnum sampleCollectionPlace;

    @Column(name = "payment_status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Column(name = "lab_location")
    @Enumerated(value = EnumType.STRING)
    private AddressEnum labLocation;

    @Column(name = "home_location")
    private AddressEnum homeLocation;

    @OneToOne
    private TestPackage testPackage;

    @OneToMany
    @JoinTable(name = "order_test",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<LabTest> tests;

    @OneToOne
    private AppointmentSlot slot;

    @OneToOne
    private TestResult testResult;
}
