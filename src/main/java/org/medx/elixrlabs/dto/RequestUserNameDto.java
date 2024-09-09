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
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
}
