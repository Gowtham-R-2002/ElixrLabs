package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    List<AppointmentSlot> findBySampleCollectorId(Long id);

    List<AppointmentSlot> findBySampleCollectorIdAndIsSampleCollectedFalse(Long id);

    List<AppointmentSlot> findBySampleCollectorIdAndIsSampleCollectedTrue(Long id);

    List<AppointmentSlot> findByLocationAndTestCollectionPlaceAndDateSlot(LocationEnum location, TestCollectionPlaceEnum testCollectionPlace, LocalDate date);

}
