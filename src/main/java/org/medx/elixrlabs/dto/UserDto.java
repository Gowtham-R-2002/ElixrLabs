package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;
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
    @NotNull(message = "Email is required !")
    @NotBlank(message = "Email must not be blank !")
    @Email(regexp = "^[a-zA-Z]+[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+(\\.[a-z]+)+$", message = "Enter a valid email address ! Eg : user@example.com")
    private String email;

    @NotNull(message = "Password is required!")
    @NotBlank(message = "Password cannot be blank!")
    @Length(min = 6, max = 15, message = "Password must contain max 15 characters and min 6 characters !")
    private String password;

    @NotNull(message = "Date of birth is required!")
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
