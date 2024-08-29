package org.medx.elixrlabs.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestPackageDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    List<Long> labTestIds;

}
