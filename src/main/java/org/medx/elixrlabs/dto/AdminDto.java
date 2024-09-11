package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
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
    @NotNull(message = "Email field is required!")
    @NotBlank(message = "Email must not be blank!")
    @Email(regexp = "^[a-zA-Z]+[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+(\\.[a-z]+)+$", message = "Enter a valid email address ! Eg : user@example.com")
    private String email;
    @NotNull(message = "Password is required !")
    @NotBlank(message = "Password must not be blank!")
    @Length(min = 6, max = 15, message = "Password must be minimum 6 characters long and maximum 15 characters")
    private String password;
    @NotNull(message = "Place is required!")
    private LocationEnum place;
}
