package org.medx.elixrlabs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.medx.elixrlabs.annotation.TimeSlot;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

import java.time.LocalDate;

@Data
@Builder
public class RequestSlotBookDto {
    @NotNull
    private LocationEnum location;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull
    private TestCollectionPlaceEnum testCollectionPlace;
}
