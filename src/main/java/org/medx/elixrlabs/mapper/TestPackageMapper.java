package org.medx.elixrlabs.mapper;

import java.util.List;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.RequestTestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.model.TestPackage;

/**
 * <p> Mapper class for mapping between TestPackage and TestPackageDTO.
 * This class provides static methods for converting between TestPackage entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate. </p>
 *
 * @author Deolin Jaffens
 */
public class TestPackageMapper {

    /**
     * Converts a {@link RequestTestPackageDto} to its respective {@link TestPackage} entity.
     *
     * @param requestTestPackageDto {@link RequestTestPackageDto} The TestPackage DTO to be converted.
     * @return {@link TestPackage} The corresponding TestPackage entity.
     */
    public static TestPackage toTestPackage(RequestTestPackageDto requestTestPackageDto) {
        return TestPackage.builder()
                .id(requestTestPackageDto.getId())
                .name(requestTestPackageDto.getName())
                .description(requestTestPackageDto.getDescription())
                .build();
    }

    /**
     * Converts an {@link TestPackage} entity to an {@link ResponseTestPackageDto}.
     *
     * @param testPackage {@link TestPackage} The TestPackage entity to be converted.
     * @return {@link ResponseTestPackageDto} The corresponding TestPackage DTO.
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

    /**
     * Converts an {@link ResponseTestPackageDto} to {@link TestPackage} entity.
     *
     * @param responseTestPackageDto {@link ResponseTestPackageDto} The test package Dto to be converted.
     * @return {@link TestPackage} The corresponding test package entity.
     */
    public static TestPackage toTestPackageFromResponseDto(ResponseTestPackageDto responseTestPackageDto) {
        return TestPackage.builder()
                .id(responseTestPackageDto.getId())
                .name(responseTestPackageDto.getName())
                .tests(responseTestPackageDto.getLabTests().stream().map(LabTestMapper::toLabTest).toList())
                .description(responseTestPackageDto.getDescription())
                .price(responseTestPackageDto.getPrice())
                .build();
    }
}
