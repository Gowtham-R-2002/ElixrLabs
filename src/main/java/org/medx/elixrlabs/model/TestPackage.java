package org.medx.elixrlabs.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>
 * The {@code TestPackage} class represents a collection of tests
 * that are grouped together as a package. This package can be ordered
 * by customers, often at a discounted rate compared to ordering
 * individual tests. Each package has a unique name, description, and
 * price, and may be subject to soft deletion via the isDeleted flag.
 * </p>
 *
 * @author Sabarinathan K
 * @version 1.0
 */
@Entity
@Table(name = "test_packages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_package_id")
    private Long id;

    @Column(name = "test_package_name", unique = true)
    private String name;

    private String description;
    private double price;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(name = "test_test_package",
            joinColumns = @JoinColumn(name = "test_package_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<LabTest> tests;

}
