//package org.medx.elixrlabs.service;
//
//import java.util.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.medx.elixrlabs.dto.SampleCollectorDto;
//import org.medx.elixrlabs.dto.UserDto;
//import org.medx.elixrlabs.helper.SecurityContextHelper;
//import org.medx.elixrlabs.model.Role;
//import org.medx.elixrlabs.model.SampleCollector;
//import org.medx.elixrlabs.model.User;
//import org.medx.elixrlabs.repository.SampleCollectorRepository;
//import org.medx.elixrlabs.service.impl.SampleCollectorServiceImpl;
//import org.medx.elixrlabs.util.LocationEnum;
//import org.medx.elixrlabs.util.RoleEnum;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class SampleCollectorServiceImplTest {
//
//    @InjectMocks
//    private SampleCollectorServiceImpl sampleCollectorService;
//
//    @Mock
//    private SampleCollectorRepository sampleCollectorRepository;
//
//    @Mock
//    private RoleService roleService;
//
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    private UserDto userDto;
//    private SampleCollector sampleCollector;
//    private List<SampleCollector> sampleCollectors;
//    private String email;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        userDto = UserDto.builder()
//                .email("sabari@gmail.com")
//                .password("sabari@123")
//                .build();
//
//        User user1 = User.builder()
//                .UUID("1234-5678")
//                .email("sabari@gmail.com")
//                .isDeleted(false)
//                .build();
//
//        User user2 = User.builder()
//                .UUID("1234-5678-9876")
//                .email("sabari@gmail.com")
//                .password("sabari@123")
//                .isDeleted(false)
//                .build();
//
//        sampleCollector = SampleCollector.builder()
//                .user(user1)
//                .id(1L)
//                .isVerified(true)
//                .build();
//
//        SampleCollector sampleCollector2 = SampleCollector.builder()
//                .id(2L)
//                .user(user2)
//                .isVerified(true)
//                .build();
//
//        sampleCollectors = Arrays.asList(sampleCollector, sampleCollector2);
//
//        email = "sabari@gamail.com";
//
//    }
//
//
//    @Test
//    void testCreateOrUpdateSampleCollector_positive() {
//        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenReturn(sampleCollector);
//        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        when(roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR)).thenReturn(new Role());
//        when(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)).thenReturn(new Role());
//        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenReturn(sampleCollector);
//        SampleCollectorDto result = sampleCollectorService.createOrUpdateSampleCollector(userDto);
//        assertNotNull(result);
//        verify(sampleCollectorRepository).save(any(SampleCollector.class));
//    }
//
//    @Test
//    void testCreateOrUpdateSampleCollector_negative() {
//        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenReturn(null);
//        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        when(roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR)).thenReturn(new Role());
//        when(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)).thenReturn(new Role());
//        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenReturn(sampleCollector);
//        SampleCollectorDto result = sampleCollectorService.createOrUpdateSampleCollector(userDto);
//        assertNotNull(result);
//    }
//
////    @Test
////    void testCreateOrUpdateSampleCollector_exception() {
////        when(sampleCollectorRepository.save(any(SampleCollector.class))).thenThrow(new RuntimeException("Database error"));
////        assertThrows(RuntimeException.class, () -> sampleCollectorService.createOrUpdateSampleCollector(userDto));
////    }
//
//    @Test
//    void testDeleteSampleCollector_positive() {
//        when(SecurityContextHelper.extractEmailFromContext()).thenReturn(email);
//        when(sampleCollectorService.getSampleCollectorByEmail(email)).thenReturn(sampleCollector);
//        when(sampleCollectorRepository.save(sampleCollector)).thenReturn(sampleCollector);
//        boolean result = sampleCollectorService.deleteSampleCollector();
//        assertTrue(result);
//        assertTrue(sampleCollector.getUser().isDeleted());
//        verify(sampleCollectorRepository).save(sampleCollector);
//    }
//
//    @Test
//    void testDeleteSampleCollector_negative() {
//        when(sampleCollectorService.getSampleCollectorByEmail(anyString())).thenReturn(null);
//        assertThrows(NullPointerException.class, () -> sampleCollectorService.deleteSampleCollector());
//    }
//
//    @Test
//    void testDeleteSampleCollector_exception() {
//        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenThrow(new RuntimeException("Database error"));
//        assertThrows(RuntimeException.class, () -> sampleCollectorService.deleteSampleCollector());
//    }
//
//    @Test
//    void testGetSampleCollectorByEmail_positive() {
//        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenReturn(sampleCollector);
//        SampleCollector result = sampleCollectorService.getSampleCollectorByEmail(email);
//        assertNotNull(result);
//        assertEquals(sampleCollector, result);
//    }
//
//    @Test
//    void testGetSampleCollectorByEmail_negative() {
//        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenReturn(null);
//        assertThrows(NoSuchElementException.class, () -> sampleCollectorService.getSampleCollectorByEmail(email));
//    }
//
//    @Test
//    void testGetSampleCollectorByEmail_exception() {
//        when(sampleCollectorRepository.getSampleCollectorByEmail(anyString())).thenThrow(new RuntimeException("Database error"));
//        assertThrows(RuntimeException.class, () -> sampleCollectorService.getSampleCollectorByEmail(email));
//    }
//
//    @Test
//    void testGetSampleCollectors_positive() {
//        when(sampleCollectorRepository.getAllSampleCollector()).thenReturn(sampleCollectors);
//        List<SampleCollectorDto> result = sampleCollectorService.getSampleCollectors();
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void testGetSampleCollectors_negative() {
//        when(sampleCollectorRepository.getAllSampleCollector()).thenReturn(new ArrayList<>());
//        List<SampleCollectorDto> result = sampleCollectorService.getSampleCollectors();
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testGetSampleCollectors_exception() {
//        when(sampleCollectorRepository.getAllSampleCollector()).thenThrow(new RuntimeException("Database error"));
//        assertThrows(RuntimeException.class, () -> sampleCollectorService.getSampleCollectors());
//    }
//
//    @Test
//    void testGetSampleCollectorByPlace_positive() {
//        when(sampleCollectorRepository.getSampleCollectorsByPlace(any(LocationEnum.class))).thenReturn(sampleCollectors);
//        List<SampleCollector> result = sampleCollectorService.getSampleCollectorByPlace(LocationEnum.GUINDY);
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void testGetSampleCollectorByPlace_negative() {
//        when(sampleCollectorRepository.getSampleCollectorsByPlace(any(LocationEnum.class))).thenReturn(new ArrayList<>());
//        List<SampleCollector> result = sampleCollectorService.getSampleCollectorByPlace(LocationEnum.GUINDY);
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testGetSampleCollectorByPlace_exception() {
//        when(sampleCollectorRepository.getSampleCollectorsByPlace(any(LocationEnum.class))).thenThrow(new RuntimeException("Database error"));
//        assertThrows(RuntimeException.class, () -> sampleCollectorService.getSampleCollectorByPlace(LocationEnum.GUINDY));
//    }
//
//}
