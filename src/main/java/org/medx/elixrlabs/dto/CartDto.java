package org.medx.elixrlabs.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartDto {
    private List<Long> testIds;
    private Long testPackageId;
}
