package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * <p>
 * This DTO is responsible for getting new Admin details.
 * Encapsulates data related to data of Admin such as username(email) and password.
 * </p>
 *
 * @author Gowtham R
 */
@Data
@Builder
public class AdminDto {
    @NotNull
    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+(\\.[a-z]+)+$", message = "Enter a valid email address !")
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    private LocationEnum place;
}
