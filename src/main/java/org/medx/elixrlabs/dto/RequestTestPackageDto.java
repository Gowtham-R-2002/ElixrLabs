package org.medx.elixrlabs.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.medx.elixrlabs.annotation.LabTests;

/**
 * <p>Represents data required for creating or updating a test package.</p>
 *
 * <p>This class contains details about the test package including its name, description,
 * price, and associated lab tests.</p>
 *
 * @author Gowtham R
 */

@Data
@Builder
public class RequestTestPackageDto {
    private long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @Min(value = 500, message = "Must be greater than 500")
    private double price;

    @LabTests
    private List<Long> labTestIds;
}
