package org.medx.elixrlabs.mapper;

import org.springframework.stereotype.Component;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.ResponseTestInCartDto;
import org.medx.elixrlabs.model.LabTest;

/**
 * <p>Mapper class for mapping between User and LabTestDto.
 * This class provides static methods for converting between LabTest entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.</p>
 *
 * @author Deolin Jaffens
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
     * @param labTestDto {@link LabTestDto} The LabTest DTO to be converted.
     * @return {@link LabTest} The corresponding LabTest entity.
     */
    public static LabTest toLabTest(LabTestDto labTestDto) {
        return LabTest.builder()
                .id(labTestDto.getId())
                .name(labTestDto.getName())
                .price(labTestDto.getPrice())
                .description(labTestDto.getDescription())
                .defaultValue(labTestDto.getDefaultValue())
                .build();
    }

    /**
     * Converts a {@link LabTest} entity to its respective {@link ResponseTestInCartDto}.
     *
     * @param labTest {@link LabTest} The LabTest entity to be converted.
     * @return {@link ResponseTestInCartDto} The corresponding LabTest dto.
     */
    public static ResponseTestInCartDto toResponseTestDto(LabTest labTest) {
        return ResponseTestInCartDto.builder()
                .id(labTest.getId())
                .name(labTest.getName())
                .price(labTest.getPrice())
                .description(labTest.getDescription())
                .build();
    }

    /**
     * Converts a {@link ResponseTestInCartDto} to its respective {@link LabTest} entity.
     *
     * @param labTestDto {@link ResponseTestInCartDto} The LabTest DTO to be converted.
     * @return {@link LabTest} The corresponding LabTest entity.
     */
    public static LabTest toLabTest(ResponseTestInCartDto labTestDto) {
        return LabTest.builder()
                .id(labTestDto.getId())
                .name(labTestDto.getName())
                .price(labTestDto.getPrice())
                .description(labTestDto.getDescription())
                .build();
    }

}
