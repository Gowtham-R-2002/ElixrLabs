package org.medx.elixrlabs.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartDto {
    private boolean isPurchased;
    private List<Long> testIds;
    private Long testPackageId;
}
