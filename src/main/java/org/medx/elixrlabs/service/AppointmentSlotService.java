package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.util.LocationEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AppointmentSlotService {
    Set<String> getAvailableSlots(SlotBookDto slotBookDto);

    boolean isSlotAvailable(SlotBookDto slotBookDto);

    OrderSuccessDto bookSlot(SlotBookDto slotBookDto);

    List<AppointmentSlot> getAppointmentsByPlace(LocationEnum location, LocalDate date);

    AppointmentSlot createOrUpdateAppointment(AppointmentSlot appointmentSlot);

    void assignSampleCollectorToAppointment(Long id, SampleCollector sampleCollector);

    void markSampleCollected(Long id);
}
