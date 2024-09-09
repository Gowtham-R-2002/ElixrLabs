package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.RequestPatientDto;
import org.medx.elixrlabs.dto.ResponsePatientDto;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.model.User;

/**
 * <p> Mapper class for mapping DTOs and entity related to patient.
 * This class provides static methods for converting between patient entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate. </p>
 *
 * @author Gowtham R
 */
public class PatientMapper {

    /**
     * Converts an {@link RequestPatientDto} to an {@link Patient}.
     *
     * @param patientDto {@link RequestPatientDto} The patient dto to be converted.
     * @return {@link Patient} The corresponding Patient.
     */
    public static Patient toPatient(RequestPatientDto patientDto) {
        User user = User.builder()
                .dateOfBirth(patientDto.getDateOfBirth())
                .phoneNumber(patientDto.getPhoneNumber())
                .email(patientDto.getEmail())
                .password(patientDto.getPassword())
                .place(patientDto.getPlace())
                .gender(patientDto.getGender())
                .build();
        return Patient.builder()
                .user(user)
                .build();
    }

    /**
     * Converts an {@link Patient} entity to an {@link RequestPatientDto}.
     *
     * @param patient {@link Patient} The Patient entity to be converted.
     * @return {@link ResponsePatientDto} The corresponding DTO of patient.
     */
    public static ResponsePatientDto toPatientDto(Patient patient) {
        return ResponsePatientDto.builder()
                .dateOfBirth(patient.getUser().getDateOfBirth())
                .email(patient.getUser().getEmail())
                .gender(patient.getUser().getGender())
                .place(patient.getUser().getPlace())
                .phoneNumber(patient.getUser().getPhoneNumber())
                .id(patient.getId())
                .build();
    }
}
