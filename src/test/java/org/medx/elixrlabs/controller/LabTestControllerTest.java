package org.medx.elixrlabs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.service.LabTestService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LabTestControllerTest {

    @Mock
    private LabTestService labTestService;

    @InjectMocks
    private LabTestController labTestController;

    private LabTestDto requestLabTestDto;
    private LabTestDto responseLabTestDto;

    @BeforeEach
    void setUp() {
        requestLabTestDto = LabTestDto.builder()
                .name("Blood Test")
                .defaultValue("BPC 1000")
                .description("Simple Blood Test")
                .price(200.00)
                .build();
        requestLabTestDto = LabTestDto.builder()
                .id(1L)
                .name("Blood Test")
                .defaultValue("BPC 1000")
                .description("Simple Blood Test")
                .price(200.00)
                .build();
    }

}
