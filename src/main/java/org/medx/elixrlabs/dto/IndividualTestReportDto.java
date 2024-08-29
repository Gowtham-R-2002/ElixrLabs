package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IndividualTestReportDto {
    private String testName;
    private String actualValue;
    private String presentValue;
}
