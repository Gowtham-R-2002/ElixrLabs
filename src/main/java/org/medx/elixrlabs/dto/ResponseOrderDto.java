package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.medx.elixrlabs.util.TestStatusEnum;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderDto {
    private long id;
    private List<LabTestDto> tests;
    private ResponseTestPackageDto testPackageDto;
    private TestStatusEnum testStatus;
}
