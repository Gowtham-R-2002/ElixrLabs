package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Encapsulates data required for submitting a test result.</p>
 *
 * <p>This class contains the test ID and the result of the test, necessary for processing
 * and recording test outcomes.</p>
 *
 * @author Gowtham R
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTestIdWithResultDto {
    @NotNull
    private long testId;

    @NotNull
    @NotBlank
    private String result;
}
