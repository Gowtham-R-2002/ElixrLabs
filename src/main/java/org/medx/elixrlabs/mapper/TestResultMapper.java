package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.model.TestResult;

/**
 * Mapper class for mapping DTOs and entity related to test result.
 *
 * <p>
 * This class provides static methods for converting between User entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */

public class TestResultMapper {

    /**
     * Converts an {@link TestResult} entity to an {@link TestResultDto}.
     *
     * @param testResult {@link TestResult} The test result entity to be converted.
     * @return {@link TestResultDto} The corresponding test result DTO.
     */

    public static TestResultDto toTestResultDto(TestResult testResult) {
        return TestResultDto.builder()
                .id(testResult.getId())
                .email(testResult.getName())
                .ageAndGender(testResult.getAgeAndGender())
                .result(testResult.getResult())
                .orderDate(testResult.getOrderDate())
                .generatedAt(testResult.getGeneratedAt())
                .build();
    }

    /**
     * Converts an {@link TestResultDto} dto to an {@link TestResult}.
     *
     * @param testResultDto {@link TestResultDto} The test result entity to be converted.
     * @return {@link TestResult} The corresponding test result DTO.
     */

    public static TestResult toTestResult(TestResultDto testResultDto) {
        return TestResult.builder()
                .id(testResultDto.getId())
                .result(testResultDto.getResult())
                .generatedAt(testResultDto.getGeneratedAt())
                .build();
    }
}
