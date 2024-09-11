package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import org.medx.elixrlabs.annotation.TimeSlot;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

/**
 * <p>Represents the data required for booking an appointment slot.</p>
 *
 * <p>Includes details such as location, date, time slot, and the place for test collection.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
public class SlotBookDto {

    @NotNull
    private LocationEnum location;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @TimeSlot
    private String timeSlot;

    @NotNull
    private TestCollectionPlaceEnum testCollectionPlace;

    @NotNull
    private String address;

}
