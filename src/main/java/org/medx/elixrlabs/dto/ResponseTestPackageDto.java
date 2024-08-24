package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResponseTestPackageDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Map<Long, String> labTests;
}
