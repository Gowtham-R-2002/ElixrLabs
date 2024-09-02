//package org.medx.elixrlabs.controller;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.medx.elixrlabs.dto.SampleCollectorDto;
//import org.medx.elixrlabs.dto.UserDto;
//import org.medx.elixrlabs.model.User;
//import org.medx.elixrlabs.service.SampleCollectorService;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class SampleCollectorControllerTest {
//
//    @InjectMocks
//    private SampleCollectorController sampleCollectorController;
//
//    @Mock
//    private SampleCollectorService sampleCollectorService;
//
//    private UserDto userDto;
//    private SampleCollectorDto sampleCollectorDto1;
//    private SampleCollectorDto sampleCollectorDto2;
//    private List<SampleCollectorDto> sampleCollectorDtos;
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
//                .build();
//
//        User user2 = User.builder()
//                .UUID("1234-5678-9876")
//                .email("deo@gmail.com")
//                .build();
//
//        sampleCollectorDto1 = SampleCollectorDto.builder()
//                .user(user1)
//                .isVerified(false)
//                .build();
//
//        sampleCollectorDto2 = SampleCollectorDto.builder()
//                .user(user2)
//                .isVerified(false)
//                .build();
//
//        sampleCollectorDtos = Arrays.asList(sampleCollectorDto1, sampleCollectorDto2);
//    }
//
//    @Test
//    void testCreateSampleCollector_positive() {
//        when(sampleCollectorService.createOrUpdateSampleCollector(any(UserDto.class))).thenReturn(sampleCollectorDto1);
//
//        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.createSampleCollector(userDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(sampleCollectorService).createOrUpdateSampleCollector(userDto);
//    }
//
//    @Test
//    void testCreateSampleCollector_negative() {
//        when(sampleCollectorService.createOrUpdateSampleCollector(any(UserDto.class))).thenReturn(null);
//
//        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.createSampleCollector(userDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(sampleCollectorService).createOrUpdateSampleCollector(userDto);
//    }
//
//    @Test
//    void testCreateSampleCollector_exception() {
//        when(sampleCollectorService.createOrUpdateSampleCollector(any(UserDto.class))).thenThrow(new RuntimeException("Exception occurred"));
//
//        Exception exception = assertThrows(RuntimeException.class,
//                () -> sampleCollectorController.createSampleCollector(userDto));
//
//        assertEquals("Exception occurred", exception.getMessage());
//        verify(sampleCollectorService).createOrUpdateSampleCollector(userDto);
//    }
//
//    @Test
//    void testGetAllSampleCollector_positive() {
//        when(sampleCollectorService.getSampleCollectors()).thenReturn(sampleCollectorDtos);
//
//        ResponseEntity<List<SampleCollectorDto>> response = sampleCollectorController.getAllSampleCollector();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(2, response.getBody().size());
//        verify(sampleCollectorService).getSampleCollectors();
//    }
//
//    @Test
//    void testGetAllSampleCollector_negative() {
//        when(sampleCollectorService.getSampleCollectors()).thenReturn(new ArrayList<>());
//
//        ResponseEntity<List<SampleCollectorDto>> response = sampleCollectorController.getAllSampleCollector();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody().isEmpty());
//        verify(sampleCollectorService).getSampleCollectors();
//    }
//
//    @Test
//    void testGetAllSampleCollector_exception() {
//        when(sampleCollectorService.getSampleCollectors()).thenThrow(new RuntimeException("Exception occurred"));
//
//        Exception exception = assertThrows(RuntimeException.class,
//                () -> sampleCollectorController.getAllSampleCollector());
//
//        assertEquals("Exception occurred", exception.getMessage());
//        verify(sampleCollectorService).getSampleCollectors();
//    }
//
//    @Test
//    void testGetSampleCollectorById_positive() {
//        when(sampleCollectorService.getSampleCollectorById(anyLong())).thenReturn(sampleCollectorDto2);
//
//        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.getSampleCollectorById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(sampleCollectorService).getSampleCollectorById(1L);
//    }
//
//    @Test
//    void testGetSampleCollectorById_negative() {
//        when(sampleCollectorService.getSampleCollectorById(anyLong())).thenReturn(null);
//
//        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.getSampleCollectorById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(sampleCollectorService).getSampleCollectorById(1L);
//    }
//
//    @Test
//    void testGetSampleCollectorById_exception() {
//        when(sampleCollectorService.getSampleCollectorById(anyLong())).thenThrow(new NoSuchElementException("SampleCollector not found"));
//
//        Exception exception = assertThrows(NoSuchElementException.class,
//                () -> sampleCollectorController.getSampleCollectorById(1L));
//
//        assertEquals("SampleCollector not found", exception.getMessage());
//        verify(sampleCollectorService).getSampleCollectorById(1L);
//    }
//
//    @Test
//    void testUpdateSampleCollector_positive() {
//        when(sampleCollectorService.createOrUpdateSampleCollector(any(UserDto.class))).thenReturn(sampleCollectorDto1);
//
//        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.updateSampleCollector(userDto);
//
//        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(sampleCollectorService).createOrUpdateSampleCollector(userDto);
//    }
//
//    @Test
//    void testUpdateSampleCollector_negative() {
//        when(sampleCollectorService.createOrUpdateSampleCollector(any(UserDto.class))).thenReturn(null);
//
//        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.updateSampleCollector(userDto);
//
//        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(sampleCollectorService).createOrUpdateSampleCollector(userDto);
//    }
//
//    @Test
//    void testUpdateSampleCollector_exception() {
//        when(sampleCollectorService.createOrUpdateSampleCollector(any(UserDto.class))).thenThrow(new RuntimeException("Exception occurred"));
//
//        Exception exception = assertThrows(RuntimeException.class,
//                () -> sampleCollectorController.updateSampleCollector(userDto));
//
//        assertEquals("Exception occurred", exception.getMessage());
//        verify(sampleCollectorService).createOrUpdateSampleCollector(userDto);
//    }
//
//    @Test
//    void testDeleteSampleCollector_positive() {
//        when(sampleCollectorService.deleteSampleCollector()).thenReturn(true);
//
//        ResponseEntity<Boolean> response = sampleCollectorController.deleteSampleCollector();
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(sampleCollectorService).deleteSampleCollector();
//    }
//
//    @Test
//    void testDeleteSampleCollector_negative() {
//        when(sampleCollectorService.deleteSampleCollector()).thenReturn(false);
//
//        ResponseEntity<Boolean> response = sampleCollectorController.deleteSampleCollector();
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(sampleCollectorService).deleteSampleCollector();
//    }
//
//    @Test
//    void testDeleteSampleCollector_exception() {
//        when(sampleCollectorService.deleteSampleCollector()).thenThrow(new RuntimeException("Exception occurred"));
//
//        Exception exception = assertThrows(RuntimeException.class,
//                () -> sampleCollectorController.deleteSampleCollector());
//
//        assertEquals("Exception occurred", exception.getMessage());
//        verify(sampleCollectorService).deleteSampleCollector();
//    }
//}
