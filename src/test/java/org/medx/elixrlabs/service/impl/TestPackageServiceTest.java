package org.medx.elixrlabs.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.dto.RequestTestPackageDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.repository.TestPackageRepository;
import org.medx.elixrlabs.service.LabTestService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestPackageServiceTest {

    @InjectMocks
    private TestPackageServiceImpl testPackageService;

    @Mock
    private TestPackageRepository testPackageRepository;

    @Mock
    private LabTestService labTestService;

    private RequestTestPackageDto requestTestPackageDto;
    private TestPackage testPackage;
    private LabTestDto labTestDto;
    private List<TestPackage> testPackages;

    @BeforeEach
    void setUp() {

        LabTest labTest = LabTest.builder()
                .id(1L)
                .isDeleted(false)
                .build();

        LabTest labTest2 = LabTest.builder()
                .id(2L)
                .isDeleted(false)
                .build();

        labTestDto = LabTestDto.builder()
                .id(1L)
                .name("Complete blood count (CBC)")
                .build();

        requestTestPackageDto = RequestTestPackageDto.builder()
                .labTestIds(List.of(1L, 2L))
                .build();

        testPackage = TestPackage.builder()
                .tests(List.of(labTest, labTest2))
                .build();

        TestPackage testPackage2 = TestPackage.builder()
                .tests(List.of(labTest2))
                .build();

        testPackages = Arrays.asList(testPackage, testPackage2);
    }

    @Test
    void testCreateOrUpdateTest_positive() {
        when(labTestService.getLabTestById(anyLong())).thenReturn(labTestDto);
        when(testPackageRepository.save(any(TestPackage.class))).thenReturn(testPackage);

        ResponseTestPackageDto response = testPackageService.createOrUpdateTestPackage(requestTestPackageDto);

        assertNotNull(response);
        verify(testPackageRepository).save(any(TestPackage.class));
    }

    @Test
    void testCreateOrUpdateTest_negative() {
        when(labTestService.getLabTestById(anyLong())).thenReturn(null);
        assertThrows(NullPointerException.class,
                () -> testPackageService.createOrUpdateTestPackage(requestTestPackageDto));
        verify(testPackageRepository, never()).save(any(TestPackage.class));
    }

    @Test
    void testGetAllTestPackages_positive() {
        when(testPackageRepository.findByIsDeletedFalse()).thenReturn(testPackages);

        List<ResponseTestPackageDto> response = testPackageService.getAllTestPackages();

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    void testGetAllTestPackages_negative() {
        when(testPackageRepository.findByIsDeletedFalse()).thenReturn(new ArrayList<>());

        List<ResponseTestPackageDto> response = testPackageService.getAllTestPackages();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void testGetAllTestPackages_exception() {
        when(testPackageRepository.findByIsDeletedFalse()).thenThrow(RuntimeException.class);

        Exception exception = assertThrows(LabException.class,
                () -> testPackageService.getAllTestPackages());

        assertEquals("Error occurred while retrieving TestPackages", exception.getMessage());
        verify(testPackageRepository).findByIsDeletedFalse();
    }

    @Test
    void testGetTestPackageById_positive() {
        when(testPackageRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(testPackage);

        ResponseTestPackageDto response = testPackageService.getTestPackageById(1L);

        assertNotNull(response);
        verify(testPackageRepository).findByIdAndIsDeletedFalse(1L);
    }

    @Test
    void testGetTestPackageById_negative() {
        when(testPackageRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(null);

        Exception exception = assertThrows(LabException.class,
                () -> testPackageService.getTestPackageById(1L));

        assertEquals("Error occurred while retrieving TestPackage" + 1L, exception.getMessage());
        verify(testPackageRepository).findByIdAndIsDeletedFalse(1L);
    }

    @Test
    void testGetTestPackageById_exception() {
        when(testPackageRepository.findByIdAndIsDeletedFalse(anyLong())).thenThrow(RuntimeException.class);

        Exception exception = assertThrows(RuntimeException.class,
                () -> testPackageService.getTestPackageById(1L));

        assertEquals("Error occurred while retrieving TestPackage" + 1L, exception.getMessage());
        verify(testPackageRepository).findByIdAndIsDeletedFalse(1L);
    }

    @Test
    void testDeleteTestPackageById_positive() {
        when(testPackageRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(testPackage);

        boolean result = testPackageService.deleteTestPackageById(1L);

        assertTrue(result);
        verify(testPackageRepository).save(any(TestPackage.class));
    }

    @Test
    void testDeleteTestPackageById_negative() {
        when(testPackageRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(null);

        Exception exception = assertThrows(LabException.class,
                () -> testPackageService.deleteTestPackageById(1L));

        assertEquals("Error occurred while deleting TestPackage" + 1L, exception.getMessage());
        verify(testPackageRepository, never()).save(any(TestPackage.class));
    }

    @Test
    void testDeleteTestPackageById_exception() {
        when(testPackageRepository.findByIdAndIsDeletedFalse(anyLong())).thenThrow(RuntimeException.class);

        Exception exception = assertThrows(LabException.class,
                () -> testPackageService.deleteTestPackageById(1L));

        assertEquals("Error occurred while deleting TestPackage" + 1L, exception.getMessage());
        verify(testPackageRepository, never()).save(any(TestPackage.class));
    }
}
