package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Encapsulates details of an OTP (One-Time Password) for verification purposes.</p>
 *
 * <p>This class contains the OTP value required for user authentication or verification.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private String otp;
}
