package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AppointmentDto {
    private String userName;
    private String timeSlot;
    private LocalDate appointmentDate;
    private Long appointmentId;
}
