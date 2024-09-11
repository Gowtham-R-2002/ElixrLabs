package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
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
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPatientDto {
    @NotBlank(message = "Email cannot be blank!")
    @NotNull(message = "Email cannot be blank!")
    @Email(regexp = "^[a-zA-Z]+[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+(\\.[a-z]+)+$",
            message = "Enter a valid email address ! Eg : user@example.com")
    private String email;
    @NotNull(message = "Password cannot be null !")
    @NotBlank(message = "Password cannot be blank !")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",
            message = "Password must contain atleast one uppercase character, one lowercase character, one symbol and a number")
    private String password;
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
