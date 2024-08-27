package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.OrderDto;
import org.medx.elixrlabs.dto.SlotBookDto;

import java.util.Set;

public interface AppointmentSlotService {
    Set<String> getAvailableSlots(SlotBookDto slotBookDto);

    boolean isSlotAvailable(SlotBookDto slotBookDto);

    OrderDto bookSlot(SlotBookDto slotBookDto);
}
