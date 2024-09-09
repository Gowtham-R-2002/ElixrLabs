package org.medx.elixrlabs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.RequestSlotBookDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * <p>
 * Interface for AppointmentSlotService, manages all the operations that are related to the
 * slot allocations. This interface is implemented by AppointmentServiceImpl
 * </p>
 */
public interface AppointmentSlotService {

    /**
     * Fetches all the available slots for the specified date and location
     *
     * @param slotBookDto {@link RequestSlotBookDto} contains date and location
     *                                              for which slots available
     *                                              has to be checked
     * @return set of available slots for the required date and location
     */
    Set<String> getAvailableSlots(RequestSlotBookDto slotBookDto);

    /**
     * Books a new order for the patient in the available slot
     *
     * @param slotBookDto the slot for which the order has to be booked
     * @return order that has been booked
     */

    OrderSuccessDto bookSlot(SlotBookDto slotBookDto);

    /**
     * Fetches all the appointment slots for a specific location
     *
     * @param location location from which the slots have to be fetched
     * @param date     date from which slots has to be fetched
     * @return Appointments in a specific location
     */

    List<AppointmentDto> getAppointmentsByPlace(LocationEnum location, LocalDate date);

    /**
     * Assigns a specific sample collector to a specific appointment
     *
     * @param id id of the appointment slot
     */
    void assignSampleCollectorToAppointment(Long id);

    /**
     * Changes the order status of a specific order
     *
     * @param id id of the appointment slot that has to get its status changed
     *           to IN_PROGRESS
     */

    void markSampleCollected(Long id);

    /**
     * Fetches all the appointments assigned to a specific sample collector.
     *
     * @return All the appointments of a specific sample collector
     */

    List<AppointmentDto> getAppointmentsBySampleCollector();

    /**
     * Fetches all the appointments that has to be completed by the sample collector.
     *
     * @return list of appointments that the sample collector collected
     */

    List<AppointmentDto> getCollectedAppointmentsBySampleCollector();

    /**
     * Fetches all the pending appointments of the sample collector.
     *
     * @return list of appointments that the sample collector has not collected
     */

    List<AppointmentDto> getPendingAppointmentsBySampleCollector();
}
