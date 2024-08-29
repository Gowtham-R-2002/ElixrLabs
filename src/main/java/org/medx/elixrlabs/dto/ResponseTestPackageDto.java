package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseTestPackageDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private List<LabTestDto> labTests;
}
