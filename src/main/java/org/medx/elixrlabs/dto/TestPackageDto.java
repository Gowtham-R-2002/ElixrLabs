package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestPackageDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    List<Long> labTestIds;

}
