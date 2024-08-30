package org.medx.elixrlabs.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.dto.RequestTestPackageDto;
import org.medx.elixrlabs.service.TestPackageService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestPackageControllerTest {

    @InjectMocks
    private TestPackageController testPackageController;

    @Mock
    private TestPackageService testPackageService;

    private RequestTestPackageDto requestTestPackageDto;
    private ResponseTestPackageDto responseTestPackageDto;

    private List<ResponseTestPackageDto> responseTestPackageDtos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestTestPackageDto = RequestTestPackageDto.builder()
                .labTestIds(List.of(1L, 2L))
                .build();

        LabTestDto labTestDto1 = LabTestDto.builder()
                .id(1L)
                .name("Complete blood count (CBC)")
                .build();

        LabTestDto labTestDto2 = LabTestDto.builder()
                .id(2L)
                .name("Basic metabolic panel (BMP)")
                .build();

        responseTestPackageDto = ResponseTestPackageDto.builder()
                .labTests(List.of(labTestDto1))
                .build();


        ResponseTestPackageDto responseTestPackageDto2 = ResponseTestPackageDto.builder()
                .labTests(List.of(labTestDto1, labTestDto2))
                .build();
        responseTestPackageDtos = Arrays.asList(responseTestPackageDto, responseTestPackageDto2);
    }

    @Test
    void testCreateTestPackage_positive() {

        when(testPackageService.createOrUpdateTestPackage(any(RequestTestPackageDto.class)))
                .thenReturn(responseTestPackageDto);

        ResponseEntity<ResponseTestPackageDto> response = testPackageController
                .createTestPackage(requestTestPackageDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseTestPackageDto, response.getBody());
        verify(testPackageService, times(1)).createOrUpdateTestPackage(requestTestPackageDto);
    }

    @Test
    void testCreateTestPackage_negative() {

        when(testPackageService.createOrUpdateTestPackage(any(RequestTestPackageDto.class))).thenReturn(null);

        ResponseEntity<ResponseTestPackageDto> response = testPackageController
                .createTestPackage(requestTestPackageDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
        verify(testPackageService, times(1)).createOrUpdateTestPackage(requestTestPackageDto);
    }

    @Test
    void testCreateTestPackage_exception() {

        when(testPackageService.createOrUpdateTestPackage(any(RequestTestPackageDto.class)))
                .thenThrow(new RuntimeException("Exception occurred"));

        assertThrows(RuntimeException.class,
                () -> testPackageController.createTestPackage(requestTestPackageDto));

        verify(testPackageService, times(1)).createOrUpdateTestPackage(requestTestPackageDto);
    }

    @Test
    void testGetAllTestPackages_positive() {

        when(testPackageService.getAllTestPackages()).thenReturn(responseTestPackageDtos);

        ResponseEntity<List<ResponseTestPackageDto>> response = testPackageController
                .getAllTestPackages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseTestPackageDtos, response.getBody());
        verify(testPackageService, times(1)).getAllTestPackages();
    }

    @Test
    void testGetAllTestPackages_negative() {
        when(testPackageService.getAllTestPackages()).thenReturn(null);

        ResponseEntity<List<ResponseTestPackageDto>> response = testPackageController
                .getAllTestPackages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(testPackageService, times(1)).getAllTestPackages();
    }

    @Test
    void testGetAllTestPackages_exception() {
        when(testPackageService.getAllTestPackages())
                .thenThrow(new RuntimeException("Exception occurred"));

        assertThrows(RuntimeException.class, () -> testPackageController.getAllTestPackages());

        verify(testPackageService, times(1)).getAllTestPackages();
    }

    @Test
    void testGetTestPackageById_positive() {

        when(testPackageService.getTestPackageById(anyLong())).thenReturn(responseTestPackageDto);

        ResponseEntity<ResponseTestPackageDto> response = testPackageController
                .getTestPackageById(anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseTestPackageDto, response.getBody());
        verify(testPackageService, times(1)).getTestPackageById(anyLong());
    }

    @Test
    void testGetTestPackageById_negative() {

        when(testPackageService.getTestPackageById(anyLong())).thenReturn(null);

        ResponseEntity<ResponseTestPackageDto> response = testPackageController
                .getTestPackageById(anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(testPackageService, times(1)).getTestPackageById(anyLong());
    }

    @Test
    void testGetTestPackageById_exception() {

        when(testPackageService.getTestPackageById(anyLong()))
                .thenThrow(new RuntimeException("Exception occurred"));

        assertThrows(RuntimeException.class,
                () -> testPackageController.getTestPackageById(anyLong()));

        verify(testPackageService, times(1)).getTestPackageById(anyLong());
    }

    @Test
    void testUpdateTestPackageById_positive() {

        when(testPackageService.createOrUpdateTestPackage(any(RequestTestPackageDto.class)))
                .thenReturn(responseTestPackageDto);

        ResponseEntity<ResponseTestPackageDto> response = testPackageController
                .updateTestPackageById(requestTestPackageDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(responseTestPackageDto, response.getBody());
        verify(testPackageService, times(1)).createOrUpdateTestPackage(requestTestPackageDto);
    }

    @Test
    void testUpdateTestPackageById_negative() {

        when(testPackageService.createOrUpdateTestPackage(any(RequestTestPackageDto.class))).thenReturn(null);

        ResponseEntity<ResponseTestPackageDto> response = testPackageController
                .updateTestPackageById(requestTestPackageDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNull(response.getBody());
        verify(testPackageService, times(1)).createOrUpdateTestPackage(requestTestPackageDto);
    }

    @Test
    void testUpdateTestPackageById_exception() {

        when(testPackageService.createOrUpdateTestPackage(any(RequestTestPackageDto.class)))
                .thenThrow(new RuntimeException("Exception occurred"));

        assertThrows(RuntimeException.class,
                () -> testPackageController.updateTestPackageById(requestTestPackageDto));

        verify(testPackageService, times(1)).createOrUpdateTestPackage(requestTestPackageDto);
    }

    @Test
    void testRemoveTestPackageById_positive() {

        when(testPackageService.deleteTestPackageById(anyLong())).thenReturn(true);

        ResponseEntity<Boolean> response = testPackageController.removeTestPackageById(anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(testPackageService, times(1)).deleteTestPackageById(anyLong());
    }

    @Test
    void testRemoveTestPackageById_negative() {

        when(testPackageService.deleteTestPackageById(anyLong())).thenReturn(false);

        ResponseEntity<Boolean> response = testPackageController.removeTestPackageById(anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(testPackageService, times(1)).deleteTestPackageById(anyLong());
    }

    @Test
    void testRemoveTestPackageById_exception() {

        when(testPackageService.deleteTestPackageById(anyLong()))
                .thenThrow(new RuntimeException("Exception occurred"));

        assertThrows(RuntimeException.class,
                () -> testPackageController.removeTestPackageById(anyLong()));

        verify(testPackageService, times(1)).deleteTestPackageById(anyLong());
    }
}

