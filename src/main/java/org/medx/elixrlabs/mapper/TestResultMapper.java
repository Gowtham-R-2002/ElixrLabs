package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.model.TestResult;

public class TestResultMapper {

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

}
