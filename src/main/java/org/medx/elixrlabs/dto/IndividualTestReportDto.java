package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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
