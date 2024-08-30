package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.util.LocationEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Interface for AppointmentSlotService, manages all the operations that are related to the
 * slot allocations. This interface is implemented by AppointmentServiceImpl
 */
public interface AppointmentSlotService {

    /**
     * Fetches all the available slots
     *
     * @param slotBookDto
     * @return
     */
    Set<String> getAvailableSlots(SlotBookDto slotBookDto);

    boolean isSlotAvailable(SlotBookDto slotBookDto);

    OrderSuccessDto bookSlot(SlotBookDto slotBookDto);

    List<AppointmentSlot> getAppointmentsByPlace(LocationEnum location, LocalDate date);

    AppointmentSlot createOrUpdateAppointment(AppointmentSlot appointmentSlot);

    void assignSampleCollectorToAppointment(Long id, SampleCollector sampleCollector);

    void markSampleCollected(Long id);

    List<AppointmentSlot> getAppointmentsBySampleCollector(Long id);

    List<AppointmentSlot> getCollectedAppointmentsBySampleCollector(Long id);

    List<AppointmentSlot> getPendingAppointmentsBySampleCollector(Long id);
}
