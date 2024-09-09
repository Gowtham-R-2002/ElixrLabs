package org.medx.elixrlabs.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the contents of a cart, including selected tests and test packages.
 *
 * @author Deolin Jaffens
 */
@Builder
@Data
public class CartDto {

    private List<Long> testIds;

    private Long testPackageId;
}
