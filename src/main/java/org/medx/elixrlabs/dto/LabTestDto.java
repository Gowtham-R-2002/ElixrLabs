package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabTestDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private String defaultValue;
}
