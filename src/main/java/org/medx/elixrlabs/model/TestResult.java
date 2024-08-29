package org.medx.elixrlabs.model;

import java.time.LocalDateTime;

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
 * This class holds the data related to an test including their ID, result, generatedAt and
 * other relevant details.
 * The {@code TestResult} class represents the outcome of a
 * completed lab test for a particular order. Each test result is
 * associated with an order, contains the result data, and includes
 * the timestamp of when the result was generated.
 * </p>
 *
 * @author  Sabarinathan K
 * @version  1.0
 */
@Entity
@Table(name = "test_results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_result_id")
    private Long id;

    private String result;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}
