package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * This class holds the data related to an test including their ID, name, price and
 * other relevant details.
 * </p>
 *
 * @author  Sabarinathan K
 * @version  1.0
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
