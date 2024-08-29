package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.RequestPatientDto;
import org.medx.elixrlabs.dto.ResponsePatientDto;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.model.User;

public class PatientMapper {
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
