package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.model.TestResult;

public class TestResultMapper {

    public static TestResultDto toTestResultDto(TestResult testResult) {
        return TestResultDto.builder()
                .id(testResult.getId())
                .result(testResult.getResult())
                .generatedAt(testResult.getGeneratedAt())
                .build();
    }

    public static TestResult toTestResult(TestResultDto testResultDto) {
        return TestResult.builder()
                .id(testResultDto.getId())
                .result(testResultDto.getResult())
                .generatedAt(testResultDto.getGeneratedAt())
                .build();
    }
}
