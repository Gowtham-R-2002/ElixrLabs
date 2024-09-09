package org.medx.elixrlabs.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * <p>Encapsulates details of a test result.</p>
 *
 * <p>Contains information about the order date, ID, age and gender, email, test results, and the generation timestamp.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
public class TestResultDto {
    @NonNull
    private LocalDate orderDate;
    @NonNull
    private Long id;
    @NotNull
    @NotBlank
    private String ageAndGender;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @NotNull
    private List<String> result;
    @NotNull
    private LocalDateTime generatedAt;
}
