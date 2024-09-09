package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Represents the query parameters for retrieving appointments based on a specific date.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentsQueryDto {
    @NotNull
    private LocalDate date;
}
