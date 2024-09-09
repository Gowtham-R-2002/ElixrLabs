package org.medx.elixrlabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.util.LocationEnum;

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
            + " AND s.isDeleted = false AND s.isVerified = true";

    String getAllSampleCollectorQuery = "FROM SampleCollector s LEFT JOIN FETCH s.user WHERE s.isVerified = true"
            + " AND s.isDeleted = false";

    String getSampleCollectorByPlaceQuery = "FROM SampleCollector s LEFT JOIN FETCH s.user WHERE s.user.place = :place"
            + " AND s.isVerified = true AND s.isDeleted = false";

    @Query(getSampleCollectorByEmailQuery)
    SampleCollector getSampleCollectorByEmail(@Param("email") String email);

    @Query(getAllSampleCollectorQuery)
    List<SampleCollector> getAllSampleCollector();

    @Query(getSampleCollectorByPlaceQuery)
    List<SampleCollector> getSampleCollectorsByPlace(@Param("place")LocationEnum place);
}
