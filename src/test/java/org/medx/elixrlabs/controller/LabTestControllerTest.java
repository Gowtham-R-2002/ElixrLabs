package org.medx.elixrlabs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.service.LabTestService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabTestControllerTest {

    @Mock
    private LabTestService labTestService;

    @InjectMocks
    private LabTestController labTestController;

    private LabTestDto requestLabTestDto;
    private LabTestDto responseLabTestDto;
    private List<LabTestDto> labTests;

    @BeforeEach
    void setUp() {
        requestLabTestDto = LabTestDto.builder()
                .name("Blood Test")
                .defaultValue("BPC 1000")
                .description("Simple Blood Test")
                .price(200.00)
                .build();
        responseLabTestDto = LabTestDto.builder()
                .id(1L)
                .name("Blood Test")
                .defaultValue("BPC 1000")
                .description("Simple Blood Test")
                .price(200.00)
                .build();
        labTests = List.of(responseLabTestDto,responseLabTestDto);
    }

    @Test
    void testCreateLabTest() {
        when(labTestService.createOrUpdateTest(requestLabTestDto)).thenReturn(responseLabTestDto);
        ResponseEntity<LabTestDto> result = labTestController.createLabTest(requestLabTestDto);
        assertEquals(responseLabTestDto, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testGetAllLabTest() {
        when(labTestService.getAllLabTests()).thenReturn(labTests);
        ResponseEntity<List<LabTestDto>> result = labTestController.getAllLabTests();
        assertEquals(labTests, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetLabTestById() {
        when(labTestService.getLabTestById(anyLong())).thenReturn(responseLabTestDto);
        ResponseEntity<LabTestDto> result = labTestController.getLabTestById(responseLabTestDto.getId());
        assertEquals(responseLabTestDto, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateLabTestById() {
        when(labTestService.createOrUpdateTest(any(LabTestDto.class))).thenReturn(responseLabTestDto);
        ResponseEntity<LabTestDto> result = labTestController.updateLabTestById(requestLabTestDto);
        assertEquals(responseLabTestDto, result.getBody());
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void testRemoveLabTestById() {
        when(labTestService.removeLabTestById(anyLong())).thenReturn(true);
        ResponseEntity<Boolean> result = labTestController.removeLabTestById(responseLabTestDto.getId());
        assertEquals(true, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
