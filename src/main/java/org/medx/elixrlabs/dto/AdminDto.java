package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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
    private String email;
    @NotNull
    @NotBlank
    private String password;
}
