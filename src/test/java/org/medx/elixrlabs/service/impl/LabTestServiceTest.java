package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.repository.LabTestRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LabTestServiceTest {

    @Mock
    LabTestRepository labTestRepository;

    @InjectMocks
    LabTestServiceImpl labTestService;

    private LabTest labTest;
    private LabTest responseLabTest;
    private LabTestDto labTestDto;
    private LabTestDto responseLabTestDto;
    private List<LabTest> responseLabTests;
    private List<LabTestDto> responseLabTestDtos;

    @BeforeEach
    void setUp() {
        labTest = new LabTest();
        labTest = LabTest.builder()
                .name("Cancer Test")
                .description("Simple Cancer Test")
                .price(300.00)
                .defaultValue("BPC : 1000")
                .build();
        responseLabTest = new LabTest();
        responseLabTest = LabTest.builder()
                .id(12L)
                .name("Cancer Test")
                .description("Simple Cancer Test")
                .price(300.00)
                .defaultValue("BPC : 1000")
                .isDeleted(false)
                .build();
        labTestDto = new LabTestDto();
        labTestDto = LabTestDto.builder()
                .name("Cancer Test")
                .description("Simple Cancer Test")
                .price(300.00)
                .defaultValue("BPC : 1000")
                .build();
        responseLabTestDto = new LabTestDto();
        responseLabTestDto = LabTestDto.builder()
                .id(12L)
                .name("Cancer Test")
                .description("Simple Cancer Test")
                .price(300.00)
                .defaultValue("BPC : 1000")
                .build();
        responseLabTests = Arrays.asList(responseLabTest, responseLabTest);
        responseLabTestDtos = Arrays.asList(responseLabTestDto, responseLabTestDto);
    }

    @Test
    void testCreateOrUpdate_positive() {
        when(labTestRepository.save(labTest)).thenReturn(responseLabTest);
        LabTestDto result = labTestService.createOrUpdateTest(labTestDto);
        assertEquals(responseLabTestDto, result);
    }

    @Test
    void testCreateOrUpdate_negative() {
        when(labTestRepository.save(labTest)).thenThrow(LabException.class);
        assertThrows(LabException.class, () -> labTestService.createOrUpdateTest(labTestDto));
    }

    @Test
    void testGetAllLabTests_positive() {
        when(labTestRepository.findByIsDeletedFalse()).thenReturn(responseLabTests);
        List<LabTestDto> result = labTestService.getAllLabTests();
        assertEquals(responseLabTestDtos,result);
    }

    @Test
    void testGetAllLabTests_positive_testsEmpty() {
        when(labTestRepository.findByIsDeletedFalse()).thenReturn(new ArrayList<>());
        List<LabTestDto> result = labTestService.getAllLabTests();
        assertEquals(0,result.size());
    }

    @Test
    void testGetAllLabTests_negative() {
        when(labTestRepository.findByIsDeletedFalse()).thenReturn(List.of(LabTest.builder().build()));
        List<LabTestDto> result = labTestService.getAllLabTests();
        assertEquals(List.of(LabTestDto.builder().build()), result);
    }

    @Test
    void testGetAllLabTests_exception() {
        when(labTestRepository.findByIsDeletedFalse()).thenThrow(LabException.class);
        assertThrows(LabException.class, () -> labTestService.getAllLabTests());
    }

    @Test
    void testGetLabTestById_positive() {
        when(labTestRepository.findByIdAndIsDeletedFalse(responseLabTestDto.getId())).thenReturn(responseLabTest);
        LabTestDto result = labTestService.getLabTestById(responseLabTestDto.getId());
        assertEquals(result, responseLabTestDto);
    }

    @Test
    void testGetLabTestById_exception() {
        when(labTestRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> labTestService.getLabTestById(1L));
    }

    @Test
    void testRemoveLabTestById_positive() {
        when(labTestRepository.findByIdAndIsDeletedFalse(responseLabTestDto.getId())).thenReturn(responseLabTest);
        boolean result = labTestService.removeLabTestById(responseLabTestDto.getId());
        verify(labTestRepository).save(responseLabTest);
        assertTrue(result);
    }

    @Test
    void testRemoveLabTestById_negative() {
        when(labTestRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(null);
        Exception exception = assertThrows(NoSuchElementException.class, () -> labTestService.removeLabTestById(1L));
        assertEquals("Lab test Not Found with id: " + 1L, exception.getMessage());
    }
}
