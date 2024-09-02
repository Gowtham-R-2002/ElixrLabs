package org.medx.elixrlabs.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

/**
 * <p>Represents the contents of a cart, including selected tests and test packages.</p>
 *
 * @author Deolin Jaffens
 */
@Builder
@Data
public class CartDto {
    private List<Long> testIds;
    private Long testPackageId;
}
