package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.medx.elixrlabs.annotation.TimeSlot;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AppointmentDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String userName;
    @TimeSlot
    private String timeSlot;
    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private Long appointmentId;
}
