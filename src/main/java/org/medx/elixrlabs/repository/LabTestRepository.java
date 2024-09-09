package org.medx.elixrlabs.repository;

import java.util.List;

import org.medx.elixrlabs.model.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing LabTest data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for LabTest entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    List<LabTest> findByIsDeletedFalse();

    LabTest findByIdAndIsDeletedFalse(Long id);
}
