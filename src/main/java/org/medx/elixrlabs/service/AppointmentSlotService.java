package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.AvailableSlotsDto;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TimeSlotEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AppointmentSlotService {
    Set<TimeSlotEnum> getAvailableSlots(LocationEnum location, LocalDate date, TestCollectionPlaceEnum testCollectionPlace);
}
