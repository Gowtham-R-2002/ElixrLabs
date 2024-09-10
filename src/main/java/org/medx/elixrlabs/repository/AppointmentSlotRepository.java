package org.medx.elixrlabs.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing slots data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for Role entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    List<AppointmentSlot> findBySampleCollectorId(Long id);

    List<AppointmentSlot> findBySampleCollectorIdAndIsSampleCollectedFalse(Long id);

    List<AppointmentSlot> findBySampleCollectorIdAndIsSampleCollectedTrue(Long id);

    Optional<AppointmentSlot> findByIdAndTestCollectionPlace(Long id, TestCollectionPlaceEnum place);

    List<AppointmentSlot> findByLocationAndTestCollectionPlaceAndDateSlot(LocationEnum location, TestCollectionPlaceEnum testCollectionPlace, LocalDate date);

}
