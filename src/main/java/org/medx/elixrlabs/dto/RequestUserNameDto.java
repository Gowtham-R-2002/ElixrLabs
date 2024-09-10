package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Data transfer object for managing user name requests.</p>
 *
 * <p>This class encapsulates the email required for user name requests.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserNameDto {
    @NotNull
    @NotBlank
    @Email(regexp = "^[a-zA-Z]+[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+(\\.[a-z]+)+$", message = "Enter a valid email address ! Eg : user@example.com")
    private String email;
}
