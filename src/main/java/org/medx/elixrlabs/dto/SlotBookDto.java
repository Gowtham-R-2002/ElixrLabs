package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

import java.time.LocalDate;

@Data
@Builder
public class SlotBookDto {
    private LocationEnum location;
    private LocalDate date;
    private String timeSlot;
    private TestCollectionPlaceEnum testCollectionPlace;
}
