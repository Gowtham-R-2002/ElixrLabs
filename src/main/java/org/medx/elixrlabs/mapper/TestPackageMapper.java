package org.medx.elixrlabs.Mapper;

import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.TestPackage;

import java.util.HashMap;
import java.util.Map;

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
        Map<Long,String> tests = new HashMap<>();
        for(LabTest labTest : testPackage.getTests()) {
            tests.put(labTest.getId(), labTest.getName());
        }
        return ResponseTestPackageDto.builder()
                .id(testPackage.getId())
                .name(testPackage.getName())
                .description(testPackage.getDescription())
                .price(testPackage.getPrice())
                .labTests(tests)
                .build();
    }
}
