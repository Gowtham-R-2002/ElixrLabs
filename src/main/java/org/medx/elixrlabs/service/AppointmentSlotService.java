package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.AvailableSlotsDto;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentSlotService {
    List<AvailableSlotsDto> getAvailableSlots(LocationEnum location, LocalDate date, TestCollectionPlaceEnum testCollectionPlace);
}
