package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.GenderEnum;

/**
 * <p>
 * This class is responsible for transferring user data as Data Transfer Object (DTO)
 * between layers.
 * This class is used to encapsulate the data related to an user and
 * transfer it between the client and the server. DTOs are typically used to
 * decouple the internal representation of data (employee entity class) from
 * the API, providing a simpler and more controlled way to expose data.
 * </p>
 *
 * @author Gowtham R
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePatientDto {
    @NotNull
    private long id;
    @NotBlank
    @NotNull
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
}
