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

/**
 * <p>
 * This class holds the data related to an test including their ID, name, price and
 * other relevant details.
 * The {@code LabTest} class represents an individual diagnostic
 * or medical test offered by the lab. Each test has its own unique
 * name, description, and price, and is associated with a test package
 * if it belongs to one. This class also supports soft deletion
 * via the isDeleted flag.
 * </p>
 *
 * @author Sabarinathan K
 * @version 1.0
 */

@Entity
@Table(name = "tests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;

    @Column(name = "test_name", unique = true)
    private String name;

    private String description;

    private double price;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
