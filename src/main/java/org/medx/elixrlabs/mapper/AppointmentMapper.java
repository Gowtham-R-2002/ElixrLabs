package org.medx.elixrlabs.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.model.AppointmentSlot;

/**
 * Mapper class for mapping between Appointment and AppointmentDTO.
 *
 * <p>
 * This class provides static methods for converting between Appointment entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */
public class AppointmentMapper {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentMapper.class);

    /**
     * Converts an {@link AppointmentSlot} entity to an {@link AppointmentDto}.
     *
     * @param appointmentSlot {@link AppointmentSlot} The AppointmentSlot entity to be converted.
     * @return {@link AppointmentDto} The corresponding Appointment DTO.
     */
    public static AppointmentDto convertToDto(AppointmentSlot appointmentSlot) {
        logger.debug("Converting AppointmentSlot entity with ID: {} to AppointmentDTO", appointmentSlot.getId());
        AppointmentDto appointmentDto = AppointmentDto.builder()
                .appointmentId(appointmentSlot.getId())
                .appointmentDate(appointmentSlot.getDateSlot())
                .timeSlot(appointmentSlot.getTimeSlot())
                .userName(appointmentSlot.getPatient().getUser().getUsername())
                .build();
        logger.info("Converted AppointmentSlot entity to AppointmentDTO with ID: {}", appointmentSlot.getId());
        return appointmentDto;
    }
}
