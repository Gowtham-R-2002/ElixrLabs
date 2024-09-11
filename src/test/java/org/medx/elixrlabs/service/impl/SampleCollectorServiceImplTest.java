package org.medx.elixrlabs.service.impl;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.util.GenderEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.SampleCollectorRepository;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.RoleEnum;

@ExtendWith(MockitoExtension.class)
class SampleCollectorServiceImplTest {

    @InjectMocks
    private SampleCollectorServiceImpl sampleCollectorService;

    @Mock
    private SampleCollectorRepository sampleCollectorRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserDto userDto;
    private SampleCollector sampleCollector;
    private List<SampleCollector> sampleCollectors;
    private String email;
    private SampleCollectorDto sampleCollectorDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .dateOfBirth(LocalDate.of(1999, 8, 7))
                .email("samplecollector@gmail.com")
                .password("sample@123")
                .place(LocationEnum.VELACHERY)
                .phoneNumber("1234567890")
                .gender(GenderEnum.M)
                .build();

        User user1 = User.builder()
                .UUID("1234-5678")
                .dateOfBirth(LocalDate.of(1999, 8, 7))
                .email("samplecollector@gmail.com")
                .place(LocationEnum.VELACHERY)
                .phoneNumber("1234567890")
                .gender(GenderEnum.M)
                .isBlocked(false)
                .build();

        User user2 = User.builder()
                .UUID("1234-5678-9876")
                .email("samplecollector@gmail.com")
                .password("sample@123")
                .isBlocked(false)
                .build();

        sampleCollector = SampleCollector.builder()
                .user(user1)
                .id(1L)
                .isVerified(true)
                .build();

        SampleCollector sampleCollector2 = SampleCollector.builder()
                .id(2L)
                .user(user2)
                .isVerified(true)
                .build();

        sampleCollectors = Arrays.asList(sampleCollector, sampleCollector2);

        email = "samplecollector@gmail.com";

        sampleCollectorDto = SampleCollectorDto.builder()
                .place(LocationEnum.VELACHERY)
                .dateOfBirth(LocalDate.of(1999, 8, 7))
                .email("samplecollector@gmail.com")
                .phoneNumber("1234567890")
                .isVerified(true)
                .gender(GenderEnum.M)
                .id(1L)
                .build();

    }

    @Test
    void testCreateSampleCollector_positive() {
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR)).thenReturn(new Role());
        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenReturn(sampleCollector);
        SampleCollectorDto result = sampleCollectorService.createSampleCollector(userDto);
        assertEquals(sampleCollectorDto, result);
        verify(sampleCollectorRepository).save(any(SampleCollector.class));
    }

    @Test
    void testCreateSampleCollector_negative() {
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR)).thenReturn(new Role());
        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenThrow(DataIntegrityViolationException.class);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> sampleCollectorService.createSampleCollector(userDto));
        assertEquals("User already exists with email " + sampleCollectorDto.getEmail(), exception.getMessage());
    }

    @Test
    void testCreateSampleCollector_exception() {
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR)).thenReturn(new Role());
        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenThrow(RuntimeException.class);
        Exception exception = assertThrows(LabException.class, () -> sampleCollectorService.createSampleCollector(userDto));
        assertEquals("Error while saving SampleCollector with email: " + sampleCollectorDto.getEmail(), exception.getMessage());
    }

    @Test
    void testUpdateSampleCollector_positive() {
        when(sampleCollectorService.getSampleCollectorByEmail(sampleCollectorDto.getEmail())).thenReturn(sampleCollector);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR)).thenReturn(new Role());
        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenReturn(sampleCollector);
        SampleCollectorDto result = sampleCollectorService.updateSampleCollector(userDto);
        assertEquals(sampleCollectorDto, result);
    }

    @Test
    void testUpdateSampleCollector_exception() {
        when(sampleCollectorService.getSampleCollectorByEmail(sampleCollectorDto.getEmail())).thenReturn(sampleCollector);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR)).thenReturn(new Role());
        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenThrow(RuntimeException.class);
        Exception exception = assertThrows(LabException.class, () -> sampleCollectorService.updateSampleCollector(userDto));
        assertEquals("Error while updating SampleCollector with email: " + userDto.getEmail(), exception.getMessage());
    }

    @Test
    void testDeleteSampleCollector_positive() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);
            when(sampleCollectorRepository.getSampleCollectorByEmail(email)).thenReturn(sampleCollector);
            boolean result = sampleCollectorService.deleteSampleCollector();
            assertTrue(result);
            verify(sampleCollectorRepository, times(1)).save(sampleCollector);
            assertTrue(sampleCollector.isDeleted());
        }
    }

    @Test
    void testDeleteSampleCollector_exception() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);
            when(sampleCollectorRepository.getSampleCollectorByEmail(email)).thenReturn(sampleCollector);
            when(sampleCollectorRepository.save(sampleCollector)).thenThrow(new RuntimeException("Database error"));
            assertThrows(LabException.class, () -> {
                sampleCollectorService.deleteSampleCollector();
            });
            verify(sampleCollectorRepository, times(1)).save(sampleCollector);
        }
    }

    @Test
    void testGetSampleCollectorByEmail_positive() {
        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenReturn(sampleCollector);
        SampleCollector result = sampleCollectorService.getSampleCollectorByEmail(email);
        assertNotNull(result);
        assertEquals(sampleCollector, result);
    }


    @Test
    void testGetSampleCollectorByEmail_exception() {
        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> sampleCollectorService.getSampleCollectorByEmail(email));
    }

    @Test
    void testGetSampleCollectors_positive() {
        when(sampleCollectorRepository.getAllSampleCollector()).thenReturn(sampleCollectors);
        List<SampleCollectorDto> result = sampleCollectorService.getSampleCollectors();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetSampleCollectors_negative() {
        when(sampleCollectorRepository.getAllSampleCollector()).thenReturn(new ArrayList<>());
        List<SampleCollectorDto> result = sampleCollectorService.getSampleCollectors();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetSampleCollectors_exception() {
        when(sampleCollectorRepository.getAllSampleCollector()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> sampleCollectorService.getSampleCollectors());
    }

    @Test
    void testGetSampleCollectorByPlace_positive() {
        when(sampleCollectorRepository.getSampleCollectorsByPlace(any(LocationEnum.class))).thenReturn(sampleCollectors);
        List<SampleCollector> result = sampleCollectorService.getSampleCollectorByPlace(LocationEnum.GUINDY);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetSampleCollectorByPlace_negative() {
        when(sampleCollectorRepository.getSampleCollectorsByPlace(any(LocationEnum.class))).thenReturn(new ArrayList<>());
        List<SampleCollector> result = sampleCollectorService.getSampleCollectorByPlace(LocationEnum.GUINDY);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetSampleCollectorByPlace_exception() {
        when(sampleCollectorRepository.getSampleCollectorsByPlace(any(LocationEnum.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> sampleCollectorService.getSampleCollectorByPlace(LocationEnum.GUINDY));
    }

    @Test
    void testVerifySampleCollector_positive() {
        when(sampleCollectorRepository.getUnVerifiedSampleCollectorByEmail(anyString())).thenReturn(sampleCollector);
        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenReturn(sampleCollector);
        sampleCollectorService.verifySampleCollector(anyString());
        assertTrue(sampleCollector.isVerified());
    }

    @Test
    void testVerifySampleCollector_negative() {
        when(sampleCollectorRepository.getUnVerifiedSampleCollectorByEmail(anyString())).thenReturn(null);
        Exception exception = assertThrows(NoSuchElementException.class, () -> sampleCollectorService.verifySampleCollector(sampleCollectorDto.getEmail()));
        assertEquals("No Sample Collector found for username : " + sampleCollectorDto.getEmail(), exception.getMessage());
    }

    @Test
    void testGetAllSampleCollectors_positive() {
        when(sampleCollectorRepository.findAll()).thenReturn(sampleCollectors);
        List<SampleCollectorDto> result = sampleCollectorService.getAllSampleCollectors();

        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(sampleCollectorDto.getEmail(), result.getFirst().getEmail());
    }

    @Test
    void testGetAllSampleCollectors_negative() {
        when(sampleCollectorRepository.findAll()).thenReturn(null);
        Exception exception = assertThrows(NoSuchElementException.class, () -> sampleCollectorService.getAllSampleCollectors());
        assertEquals("There is no Sample Collectors", exception.getMessage());
    }
}
