package org.medx.elixrlabs.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import org.medx.elixrlabs.model.TestPackage;

/**
 * <p>Contains the details of the cart, including the list of tests, associated test package, and the total price.</p>
 *
 * @author Deolin Jaffens
 */

@Builder
@Data
public class ResponseCartDto {
    @NotNull
    private long id;
    private List<ResponseTestInCartDto> tests;
    private ResponseTestPackageDto testPackage;
    @NotNull
    private double price;
}
