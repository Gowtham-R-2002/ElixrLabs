package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AvailableSlotsDto {
    private TestCollectionPlaceEnum appointmentPlace;
    private LocationEnum location;
    private LocalDate date;
    private String time;
}
