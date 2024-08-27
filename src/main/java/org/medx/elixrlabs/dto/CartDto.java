package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CartDto {
    private boolean isPurchased;
    private List<Long> testIds;
    private Long testPackageId;
}
