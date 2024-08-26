package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.TestPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper class for mapping between TestPackage and TestPackageDTO.
 *
 * <p>
 * This class provides static methods for converting between TestPackage entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */
public class TestPackageMapper {

    /**
     * Converts an {@link TestPackageDto} to an {@link SampleCollector} entity.
     *
     * @param testPackageDto {@link TestPackageDto} The TestPackage DTO to be converted.
     * @return {@link TestPackage} The corresponding TestPackage entity.
     */
    public static TestPackage toTestPackage(TestPackageDto testPackageDto) {
        return TestPackage.builder()
                .id(testPackageDto.getId())
                .name(testPackageDto.getName())
                .description(testPackageDto.getDescription())
                .price(testPackageDto.getPrice())
                .build();
    }

    /**
     * Converts an {@link TestPackage} entity to an {@link TestPackageDto}.
     *
     * @param testPackage {@link TestPackage} The TestPackage entity to be converted.
     * @return {@link TestPackageDto} The corresponding TestPackage DTO.
     */
    public static ResponseTestPackageDto toTestPackageDto(TestPackage testPackage) {
        List<LabTestDto> tests = testPackage.getTests()
                .stream()
                .map(LabTestMapper::toRetrieveLabTestDto).toList();
        return ResponseTestPackageDto.builder()
                .id(testPackage.getId())
                .name(testPackage.getName())
                .description(testPackage.getDescription())
                .price(testPackage.getPrice())
                .labTests(tests)
                .build();
    }
}
