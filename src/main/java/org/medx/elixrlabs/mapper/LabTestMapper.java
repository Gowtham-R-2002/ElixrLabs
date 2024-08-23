package org.medx.elixrlabs.Mapper;

import org.medx.elixrlabs.dto.CreateAndRetrieveLabTestDto;
import org.medx.elixrlabs.model.LabTest;
import org.springframework.stereotype.Component;

@Component
public class LabTestMapper {

    public static CreateAndRetrieveLabTestDto toRetrieveLabTestDto(LabTest labTest) {
        return CreateAndRetrieveLabTestDto.builder()
                .id(labTest.getId())
                .name(labTest.getName())
                .price(labTest.getPrice())
                .description(labTest.getDescription())
                .defaultValue(labTest.getDefaultValue())
                .build();
    }

    public static LabTest toLabTest(CreateAndRetrieveLabTestDto createLabTestDto) {
        return LabTest.builder()
                .name(createLabTestDto.getName())
                .price(createLabTestDto.getPrice())
                .description(createLabTestDto.getDescription())
                .defaultValue(createLabTestDto.getDefaultValue())
                .build();
    }
}
