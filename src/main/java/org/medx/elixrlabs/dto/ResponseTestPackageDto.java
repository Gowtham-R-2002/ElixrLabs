package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>Represents the response data for a test package.</p>
 *
 * <p>Encapsulates information about a test package, including its unique identifier, name, description,
 * price, and a list of associated lab tests.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
public class ResponseTestPackageDto {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private double price;
    @NotNull
    private List<LabTestDto> labTests;
}
