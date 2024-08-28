package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class AppointmentDto {
    private String userName;
    private String timeSlot;
    private LocalDate appointmentDate;
    private Long appointmentId;
}
