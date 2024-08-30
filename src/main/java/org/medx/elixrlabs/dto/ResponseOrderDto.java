package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private long id;
    private List<LabTestDto> tests;
    private ResponseTestPackageDto testPackageDto;
    @NotNull
    private TestStatusEnum testStatus;
}
