package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Encapsulates the details of a lab test.</p>
 *
 * <p>This class includes information about the lab test's identifier, name, description,
 * price, and default value.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTestDto {
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @Min(value = 50, message = "Must be greater than 50")
    private double price;

    @NotNull
    @NotBlank
    private String defaultValue;
}
