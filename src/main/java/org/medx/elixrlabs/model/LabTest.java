package org.medx.elixrlabs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;

    @Column(name = "test_name")
    private String name;

    private String description;

    private double price;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
