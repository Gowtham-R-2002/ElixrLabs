package org.medx.elixrlabs.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * This class holds the data related to an Cart including their list of tests,
 * testPackage, patient and other relevant details.
 * The {@code Cart} class represents a virtual holding area where you temporarily
 * store items you want to purchase before proceeding to checkout.
 * </p>
 *
 * @version 1.0
 */
@Builder
@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean isPurchased;

    @OneToMany
    private List<LabTest> tests;

    @OneToOne
    private TestPackage testPackage;

    @OneToOne
    private Patient patient;
}
