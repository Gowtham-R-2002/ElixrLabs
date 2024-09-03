package org.medx.elixrlabs.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.mapper.TestResultMapper;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.service.TestResultService;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TestStatusEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.OrderMapper;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.PatientRepository;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.RoleEnum;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private OrderService orderService;

    @Mock
    private TestResultService testResultService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PatientServiceImpl patientService;

    private User user;
    private Order order;
    private Role patientRole;
    private Patient patient;
    private  ResponseOrderDto responseOrderDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .UUID(UUID.randomUUID().toString())
                .place(LocationEnum.GUINDY)
                .email("test@example.com")
                .password("password")
                .roles(new ArrayList<>())
                .build();

        LabTest test = LabTest.builder()
                .id(1L)
                .name("BLOOD TEST")
                .build();

        Order secondOrder = Order.builder()
                .id(2L)
                .labLocation(LocationEnum.GUINDY)
                .sampleCollectionPlace(TestCollectionPlaceEnum.HOME)
                .testStatus(TestStatusEnum.COMPLETED)
                .tests(List.of(test))
                .patient(patient)
                .orderDateTime(Calendar.getInstance())
                .build();

        patient = Patient.builder()
                .id(1L)
                .user(user)
                .isDeleted(false)
                .orders(List.of(secondOrder))
                .build();

        order = Order.builder()
                .id(1L)
                .labLocation(LocationEnum.GUINDY)
                .sampleCollectionPlace(TestCollectionPlaceEnum.HOME)
                .testStatus(TestStatusEnum.PENDING)
                .tests(List.of(test))
                .patient(patient)
                .orderDateTime(Calendar.getInstance())
                .testResult(TestResult.builder()
                        .id(1L)
                        .orderDate(LocalDate.now())
                        .build())
                .build();

        ResponseTestInCartDto responseTestInCartDto = ResponseTestInCartDto.builder()
                .id(1L)
                .build();

        responseOrderDto = ResponseOrderDto.builder()
                .id(1L)
                .tests(List.of(responseTestInCartDto))
                .build();

        patientRole = Role.builder()
                .name(RoleEnum.ROLE_PATIENT)
                .build();
    }

    @Test
    void createOrUpdatePatient_success_create() {
        UserDto userDto = new UserDto();
        userDto.setEmail("newpatient@example.com");
        userDto.setPassword("newpassword");
        userDto.setPlace(LocationEnum.GUINDY);

        when(patientRepository.findByEmailAndIsDeletedFalse(anyString())).thenReturn(patient);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)).thenReturn(patientRole);
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> {
            Patient savedPatient = invocation.getArgument(0);
            savedPatient.setId(2L);
            return savedPatient;
        });

        ResponsePatientDto result = patientService.createOrUpdatePatient(userDto);

        assertNotNull(result);
        assertEquals("newpatient@example.com", result.getEmail());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void createOrUpdatePatient_success_update() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("updatedpassword");
        userDto.setPlace(LocationEnum.GUINDY);

        when(patientRepository.findByEmailAndIsDeletedFalse(anyString())).thenReturn(patient);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedUpdatedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)).thenReturn(patientRole);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        ResponsePatientDto result = patientService.createOrUpdatePatient(userDto);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void createOrUpdatePatient_exception() {
        UserDto userDto = new UserDto();
        userDto.setEmail("error@example.com");
        userDto.setPassword("password");
        userDto.setPlace(LocationEnum.GUINDY);

        when(patientRepository.findByEmailAndIsDeletedFalse(anyString())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)).thenReturn(patientRole);
        when(patientRepository.save(any(Patient.class))).thenThrow(new RuntimeException("Database error"));

        LabException exception = assertThrows(LabException.class, () -> {
            patientService.createOrUpdatePatient(userDto);
        });

        assertTrue(exception.getMessage().contains("Error while saving Patient with email: error@example.com"));
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void getAllPatients_success() {
        List<Patient> patients = List.of(patient);
        when(patientRepository.fetchAllPatients()).thenReturn(patients);

        List<ResponsePatientDto> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
        verify(patientRepository, times(1)).fetchAllPatients();
    }

    @Test
    void getAllPatients_failure_emptyList() {
        when(patientRepository.fetchAllPatients()).thenReturn(Collections.emptyList());

        List<ResponsePatientDto> result = patientService.getAllPatients();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).fetchAllPatients();
    }

    @Test
    void getAllPatients_exception() {
        when(patientRepository.fetchAllPatients()).thenThrow(new LabException("Database error"));

        LabException exception = assertThrows(LabException.class, () -> {
            patientService.getAllPatients();
        });

        assertFalse(exception.getMessage().contains("Error while fetching patients"));
    }

    @Test
    void getOrders_success() {
        String email = "test@example.com";
        patient.setOrders(List.of(order));

        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);
            when(patientRepository.getPatientOrders(email)).thenReturn(patient);

            List<ResponseOrderDto> result = patientService.getOrders();

            assertNotNull(result);
            assertEquals(1, result.size());
            verify(patientRepository, times(1)).getPatientOrders(email);
        }
    }

    @Test
    void getOrders_failure_noOrders() {
        String email = "test@example.com";
        Patient patientWithNoOrders = patient;
        when(patientRepository.getPatientOrders(email)).thenReturn(patientWithNoOrders);

        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);

            List<ResponseOrderDto> result = patientService.getOrders();

            assertNotNull(result);
            verify(patientRepository, times(1)).getPatientOrders(email);
        }
    }

    @Test
    void getOrders_exception() {
        String email = "test@example.com";
        when(patientRepository.getPatientOrders(email)).thenThrow(new LabException("Database error"));

        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);

            LabException exception = assertThrows(LabException.class, () -> {
                patientService.getOrders();
            });

            assertEquals(true, exception.getMessage().contains("Error while fetching all orders"));
            verify(patientRepository, times(1)).getPatientOrders(email);
        }
    }

    @Test
    void getTestReport_success() {

        TestResultDto testResultDto = TestResultDto.builder()
                .id(1L)
                .orderDate(LocalDate.now())
                .build();
        when(orderService.getOrder(anyLong())).thenReturn(order);

        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            TestResultDto result = patientService.getTestReport(1L);

        assertNotNull(result);
        verify(orderService, times(2)).getOrder(1L);
        }
    }

    @Test
    void getTestReport_failure_noTestResult() {
        when(orderService.getOrder(anyLong())).thenReturn(order);
        order.getTestResult().setId(9L);
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            TestResultDto result = patientService.getTestReport(1L);
            assertNotNull(result);
            verify(orderService, times(2)).getOrder(1L);
        }
    }

    @Test
    void getTestReport_exception() {
        when(orderService.getOrder(anyLong())).thenReturn(order);
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@gmail.com");
            LabException exception = assertThrows(LabException.class, () -> {
                patientService.getTestReport(9L);
            });

            verify(orderService, times(1)).getOrder(9L);
        }
    }

    @Test
    void deletePatient_success() {
        String email = "test@example.com";
        patient.setDeleted(false);
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);
            when(patientService.getPatientByEmail(email)).thenReturn(patient);

            patientService.deletePatient();

            verify(patientRepository, times(1)).save(patient);
            assertTrue(patient.isDeleted());
        }
    }

    @Test
    void deletePatient_failure_patientNotFound() {
        String email = "nonexistent@example.com";
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);
            when(patientService.getPatientByEmail(email)).thenReturn(null);

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
                patientService.deletePatient();
            });

            verify(patientRepository, never()).save(any());
            assertTrue(exception.getMessage().contains("Patient not found with email: " + email));
        }
    }

    @Test
    void deletePatient_exception() {
        String email = "test@example.com";
        patient.setDeleted(false);
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(email);
            when(patientService.getPatientByEmail(email)).thenReturn(patient);
            doThrow(new RuntimeException("DB error")).when(patientRepository).save(patient);

            LabException exception = assertThrows(LabException.class, () -> {
                patientService.deletePatient();
            });

            verify(patientRepository, times(1)).save(patient);
            assertTrue(exception.getMessage().contains("Error while deleting Patient with email: " + email));
        }
    }

    @Test
    void getOrdersByPatient_success() {
        String email = "test@example.com";
        RequestUserNameDto requestUserNameDto = RequestUserNameDto.builder()
                .email(email)
                .build();

        when(patientRepository.getPatientOrders(email)).thenReturn(patient);

        List<ResponseOrderDto> result = patientService.getOrdersByPatient(requestUserNameDto);

        verify(patientRepository, times(1)).getPatientOrders(email);
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test
    void getOrdersByPatient_exception() {
        String email = "test@example.com";
        RequestUserNameDto requestUserNameDto = RequestUserNameDto.builder()
                .email(email)
                .build();

        when(patientRepository.getPatientOrders(email)).thenThrow(new RuntimeException("DB error"));

        LabException exception = assertThrows(LabException.class, () -> {
            patientService.getOrdersByPatient(requestUserNameDto);
        });

        verify(patientRepository, times(1)).getPatientOrders(email);
        assertTrue(exception.getMessage().contains("Error while getting orders of patient with email: " + email));
    }

    @Test
    void getPatientByEmail_success() {
        String email = "test@example.com";
        when(patientRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(patient);

        Patient result = patientService.getPatientByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getUser().getEmail());
        verify(patientRepository, times(1)).findByEmailAndIsDeletedFalse(email);
    }

    @Test
    void getPatientByEmail_failure_patientNotFound() {
        String email = "nonexistent@example.com";
        when(patientRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(null);

        Patient result = patientService.getPatientByEmail(email);

        assertNull(result);
        verify(patientRepository, times(1)).findByEmailAndIsDeletedFalse(email);
    }

    @Test
    void getPatientByEmail_exception() {
        String email = "test@example.com";
        when(patientRepository.findByEmailAndIsDeletedFalse(email)).thenThrow(new RuntimeException("Database error"));

        LabException exception = assertThrows(LabException.class, () -> {
            patientService.getPatientByEmail(email);
        });

        assertTrue(exception.getMessage().contains("Error while getting patient with email: " + email));
        verify(patientRepository, times(1)).findByEmailAndIsDeletedFalse(email);
    }
}

