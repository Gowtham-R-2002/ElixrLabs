package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * <p>Encapsulates the details of an individual test report.</p>
 *
 * <p>This class includes information about the test name, actual value of the test result,
 * and the presented value in the report.</p>
 *
 * @author Gowtham R
 */
@Builder
@Data
public class IndividualTestReportDto {
    @NotNull
    @NotBlank
    private String testName;

    @NotNull
    @NotBlank
    private String actualValue;

    @NotNull
    @NotBlank
    private String presentValue;
}
