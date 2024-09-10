package org.medx.elixrlabs.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.dto.AppointmentsQueryDto;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleCollectorControllerTest {

    @InjectMocks
    private SampleCollectorController sampleCollectorController;

    @Mock
    private SampleCollectorService sampleCollectorService;

    @Mock
    private AppointmentSlotService appointmentSlotService;

    private UserDto userDto;
    private SampleCollectorDto sampleCollectorDto1;
    private SampleCollectorDto sampleCollectorDto2;
    private List<SampleCollectorDto> sampleCollectorDtos;
    private List<AppointmentDto> appointmentDtos;
    private AppointmentsQueryDto appointmentsQueryDto;

    @BeforeEach
    void setUp() {
        appointmentsQueryDto = AppointmentsQueryDto.builder()
                .date(LocalDate.now())
                .build();

        userDto = UserDto.builder()
                .email("sabari@gmail.com")
                .password("sabari@123")
                .build();

        User user1 = User.builder()
                .UUID("1234-5678")
                .email("sabari@gmail.com")
                .build();

        User user2 = User.builder()
                .UUID("1234-5678-9876")
                .email("deo@gmail.com")
                .build();

        sampleCollectorDto1 = SampleCollectorDto.builder()
                .id(1L)
                .place(LocationEnum.VELACHERY)
                .gender(GenderEnum.M)
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(1999, 3, 2))
                .email("user1@gmail.com")
                .isVerified(false)
                .build();

        sampleCollectorDto2 = SampleCollectorDto.builder()
                .id(1L)
                .place(LocationEnum.VELACHERY)
                .gender(GenderEnum.M)
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(1989, 3, 30))
                .email("user2@gmail.com")
                .isVerified(false)
                .build();

        sampleCollectorDtos = Arrays.asList(sampleCollectorDto1, sampleCollectorDto2);

        AppointmentDto firstAppointmentDto = AppointmentDto.builder()
                .userName(sampleCollectorDto1.getEmail())
                .timeSlot("7PM")
                .appointmentDate(LocalDate.of(2024,8,29))
                .appointmentId(1L)
                .build();

        AppointmentDto secondAppointmentDto = AppointmentDto.builder()
                .userName(sampleCollectorDto2.getEmail())
                .timeSlot("7PM")
                .appointmentDate(LocalDate.of(2024,8,29))
                .appointmentId(2L)
                .build();

        appointmentDtos = List.of(firstAppointmentDto, secondAppointmentDto);
    }

    @Test
    void testCreateSampleCollector_positive() {
        when(sampleCollectorService.createSampleCollector(any(UserDto.class))).thenReturn(sampleCollectorDto1);

        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.createSampleCollector(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(sampleCollectorService).createSampleCollector(userDto);
    }

    @Test
    void testUpdateSampleCollector_positive() {
        when(sampleCollectorService.updateSampleCollector(any(UserDto.class))).thenReturn(sampleCollectorDto1);

        ResponseEntity<SampleCollectorDto> response = sampleCollectorController.updateSampleCollector(userDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(sampleCollectorService).updateSampleCollector(userDto);
    }

    @Test
    void testDeleteSampleCollector_positive() {
        when(sampleCollectorService.deleteSampleCollector()).thenReturn(true);

        ResponseEntity<Boolean> response = sampleCollectorController.deleteSampleCollector();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(sampleCollectorService).deleteSampleCollector();
    }

    @Test
    void testGetAppointments() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@gmail.com");
        when(sampleCollectorService.getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext()))
                .thenReturn(SampleCollector.builder().user(User.builder().place(LocationEnum.MARINA).build()).build());
        when(appointmentSlotService.getAppointmentsByPlace(LocationEnum.MARINA, appointmentsQueryDto.getDate())).thenReturn(appointmentDtos);
        assertEquals(2, sampleCollectorController.getAppointments(null, null, LocalDate.now()).getBody().size());
        }
    }

    @Test
    void testAssignAppointments() {
        doNothing().when(appointmentSlotService).assignSampleCollectorToAppointment(anyLong());
        ResponseEntity<Void> result = sampleCollectorController.assignAppointment(appointmentDtos.getFirst().getAppointmentId());
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void testMarkSampleCollected() {
        doNothing().when(appointmentSlotService).markSampleCollected(anyLong());
        ResponseEntity<Void> result = sampleCollectorController.markSampleCollected(appointmentDtos.getFirst().getAppointmentId());
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void testGetAllAssignedAppointments() {
        when(appointmentSlotService.getAppointmentsBySampleCollector()).thenReturn(appointmentDtos);
        ResponseEntity<List<AppointmentDto>> result = sampleCollectorController.getAppointments(true, null, null);
        assertEquals(appointmentDtos, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetCollectedAppointments() {
        when(appointmentSlotService.getCollectedAppointmentsBySampleCollector()).thenReturn(appointmentDtos);
        ResponseEntity<List<AppointmentDto>> result = sampleCollectorController.getAppointments(true, true, null);
        assertEquals(appointmentDtos, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetPendingAppointments() {
        when(appointmentSlotService.getPendingAppointmentsBySampleCollector()).thenReturn(appointmentDtos);
        ResponseEntity<List<AppointmentDto>> result = sampleCollectorController.getAppointments(true, false, null);
        assertEquals(appointmentDtos,result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}