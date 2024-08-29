package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.model.LabTest;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between User and LabTestDto.
 *
 * <p>
 * This class provides static methods for converting between LabTest entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */
@Component
public class LabTestMapper {

    /**
     * Converts an {@link LabTest} entity to an {@link LabTestDto}.
     *
     * @param labTest {@link LabTest} The LabTest entity to be converted.
     * @return {@link LabTestDto} The corresponding LabTest DTO.
     */
    public static LabTestDto toRetrieveLabTestDto(LabTest labTest) {
        return LabTestDto.builder()
                .id(labTest.getId())
                .name(labTest.getName())
                .price(labTest.getPrice())
                .description(labTest.getDescription())
                .defaultValue(labTest.getDefaultValue())
                .build();
    }

    /**
     * Converts an {@link LabTestDto} to an {@link LabTest} entity.
     *
     * @param createLabTestDto {@link LabTestDto} The LabTest DTO to be converted.
     * @return {@link LabTest} The corresponding LabTest entity.
     */
    public static LabTest toLabTest(LabTestDto createLabTestDto) {
        return LabTest.builder()
                .id(createLabTestDto.getId())
                .name(createLabTestDto.getName())
                .price(createLabTestDto.getPrice())
                .description(createLabTestDto.getDescription())
                .defaultValue(createLabTestDto.getDefaultValue())
                .build();
    }
}
