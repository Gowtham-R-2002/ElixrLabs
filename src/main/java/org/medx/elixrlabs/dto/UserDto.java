package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * <p>Represents user details required for account creation or update.</p>
 *
 * <p>Includes email, password, date of birth, phone number, gender, and location.</p>
 *
 * @author Gowtham R
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Min(value = 6)
    private String password;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank
    @NotNull
    @Size(max = 10, min = 10)
    private String phoneNumber;

    @NotNull
    private GenderEnum gender;

    @NotNull
    private LocationEnum place;
}
