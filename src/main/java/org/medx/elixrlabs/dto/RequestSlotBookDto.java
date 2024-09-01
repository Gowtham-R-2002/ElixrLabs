package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;

/**
 * <p>Encapsulates the data required for requesting a slot booking.</p>
 *
 * <p>This class contains information such as location, date, and test collection place
 * necessary for slot booking requests.</p>
 *
 * @author Gowtham R
 */

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
