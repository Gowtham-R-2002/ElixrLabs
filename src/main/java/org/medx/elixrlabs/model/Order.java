package org.medx.elixrlabs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * This class holds the data related to an user including their id, testStatus, sampleCollectionPlace,
 * paymentStatus, labLocation, homeLocation and other relevant details.
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
    private Enum testStatus;

    @Column(name = "sample_collection_place")
    private Enum sampleCollectionPlace;

    @Column(name = "payment_status")
    private Enum paymentStatus;

    @Column(name = "lab_location")
    private Enum labLocation;

    @Column(name = "home_location")
    private String homeLocation;

    //@Column(name = "test_package")
    //private TestPackage testPackage;

    private List<LabTest> tests;

    //private AppointmentSlot slot;

}
