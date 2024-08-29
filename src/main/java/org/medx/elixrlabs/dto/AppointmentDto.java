package org.medx.elixrlabs.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AppointmentDto {
    private String userName;
    private String timeSlot;
    private LocalDate appointmentDate;
    private Long appointmentId;
}
