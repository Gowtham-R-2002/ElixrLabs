package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.model.SampleCollector;

/**
 * Mapper class for mapping between SampleCollector and SampleCollectorDTO.
 *
 * <p>
 * This class provides static methods for converting between SampleCollector entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */
public class SampleCollectorMapper {

    /**
     * Converts an {@link SampleCollector} entity to an {@link SampleCollectorDto}.
     *
     * @param sampleCollector {@link SampleCollector} The SampleCollector entity to be converted.
     * @return {@link SampleCollectorDto} The corresponding SampleCollector DTO.
     */
    public static SampleCollectorDto convertToSampleCollectorDto(SampleCollector sampleCollector) {
        return SampleCollectorDto.builder()
                .id(sampleCollector.getId())
                .email(sampleCollector.getUser().getEmail())
                .dateOfBirth(sampleCollector.getUser().getDateOfBirth())
                .gender(sampleCollector.getUser().getGender())
                .phoneNumber(sampleCollector.getUser().getPhoneNumber())
                .place(sampleCollector.getUser().getPlace())
                .isVerified(sampleCollector.isVerified())
                .build();
    }
}
