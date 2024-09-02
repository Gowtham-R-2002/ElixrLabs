package org.medx.elixrlabs.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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

    @NotNull
    private double price;

    @NotNull
    private List<Long> labTestIds;
}
