package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;

import java.time.LocalDate;

/**
 * <p>
 * This class is responsible for transferring Employee data as Data Transfer Object (DTO)
 * between layers.
 * This class is used to encapsulate the data related to an Employee and
 * transfer it between the client and the server. DTOs are typically used to
 * decouple the internal representation of data (employee entity class) from
 * the API, providing a simpler and more controlled way to expose data.
 * </p>
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleCollectorDto {
    @NotNull
    private long id;
    @NotBlank
    @NotNull
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
    private String email;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 10, max = 10)
    private String phoneNumber;
    @NotNull
    private GenderEnum gender;
    @NotNull
    private LocationEnum place;
    private boolean isVerified;
}
