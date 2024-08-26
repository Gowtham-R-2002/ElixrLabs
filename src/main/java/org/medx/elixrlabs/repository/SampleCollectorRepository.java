package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.SampleCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing SampleCollector data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for SampleCollector entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
@Repository
public interface SampleCollectorRepository extends JpaRepository<SampleCollector, Long> {
    String getSampleCollectorByEmailQuery = "FROM SampleCollector s LEFT JOIN FETCH s.user WHERE s.user.email = :email"
            + " AND s.user.isDeleted = false";

    String getAllSampleCollectorQuery = "FROM SampleCollector s LEFT JOIN FETCH s.user WHERE s.isVerified = true"
            + " AND s.user.isDeleted = false";

    @Query(getSampleCollectorByEmailQuery)
    SampleCollector getSampleCollectorByEmail(@Param("email") String email);

    @Query(getAllSampleCollectorQuery)
    List<SampleCollector> getAllSampleCollector();
}
