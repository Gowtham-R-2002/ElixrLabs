package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.model.SampleCollector;

/**
 * Utility class for mapping between SampleCollector and SampleCollectorDTO.
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
     * @param sampleCollector {@link SampleCollectorDto} The SampleCollector entity to be converted.
     * @return {@link SampleCollectorDto} The corresponding SampleCollector DTO.
     */
    public static SampleCollectorDto convertToSampleCollectorDto(SampleCollector sampleCollector) {
        SampleCollectorDto sampleCollectorDto = SampleCollectorDto.builder()
                .id(sampleCollector.getId())
                .user(sampleCollector.getUser())
                .isVerified(sampleCollector.isVerified())
                .build();
        return sampleCollectorDto;
    }

    /**
     * Converts an {@link SampleCollectorDto} to an {@link SampleCollector} entity.
     *
     * @param sampleCollectorDto {@link SampleCollectorDto} The SampleCollector DTO to be converted.
     * @return {@link SampleCollector} The corresponding SampleCollector entity.
     */
    public static SampleCollector convertToSampleCollectorEntity(SampleCollectorDto sampleCollectorDto) {
        SampleCollector sampleCollector = SampleCollector.builder()
                .user(sampleCollectorDto.getUser())
                .isVerified(false)
                .build();
        return sampleCollector;
    }
}
