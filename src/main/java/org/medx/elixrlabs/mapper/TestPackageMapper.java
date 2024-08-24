package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.CreateAndRetrieveLabTestDto;
import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.TestPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class TestPackageMapper {

    public static TestPackage toTestPackage(TestPackageDto testPackageDto) {
        return TestPackage.builder()
                .id(testPackageDto.getId())
                .name(testPackageDto.getName())
                .description(testPackageDto.getDescription())
                .price(testPackageDto.getPrice())
                .build();
    }

    public static ResponseTestPackageDto toTestPackageDto(TestPackage testPackage) {
        List<CreateAndRetrieveLabTestDto> tests = testPackage.getTests()
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
