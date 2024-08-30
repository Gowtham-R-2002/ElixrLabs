package org.medx.elixrlabs.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import org.medx.elixrlabs.model.TestPackage;

@Builder
@Data
public class ResponseCartDto {
    @NotNull
    private long id;
    private List<LabTestDto> tests;
    private TestPackage testPackage;
    @NotNull
    private double price;

}
