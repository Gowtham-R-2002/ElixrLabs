package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.ResponseTestInCartDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.*;
import org.medx.elixrlabs.service.*;
import org.medx.elixrlabs.util.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabServiceTest {

    @Mock
    private OrderService orderService;

    @Mock
    private AdminService adminService;

    @Mock
    private LabTestService labTestService;

    @Mock
    private TestResultService testResultService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private LabServiceImpl labService;

    private static final MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class);

    private Admin admin;
    private User user;
    private ResponseOrderDto responseOrderDto;
    private List<ResponseTestInCartDto> tests;
    private List<LabTest> labTests;
    private Order order;
    private Patient patient;
    private TestPackage testPackage;
    private AppointmentSlot slot;
    private SampleCollector sampleCollector;
    private TestResult testResult;

    @BeforeAll
    static void staticSetUp() {
        mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn("admin@gmail.com");
    }

    @BeforeEach
    void setUp() {
        testPackage = TestPackage.builder()
                .tests(List.of(LabTest.builder()
                                .id(1L)
                                .name("Blood test")
                                .price(100)
                                .description("Simple Blood Test")
                                .build(),
                        LabTest.builder()
                                .id(2L)
                                .name("Cancer Test")
                                .description("Simple Cancer test")
                                .price(300)
                                .build()))
                .name("Test Pack")
                .price(500)
                .description("Simple Test Pack")
                .build();
        patient = Patient.builder()
                .id(1L)
                .user(User.builder()
                        .UUID(UUID.randomUUID().toString())
                        .phoneNumber("1234567890")
                        .email("user@gmail.com")
                        .roles(List.of(Role.builder()
                                .id(1)
                                .name(RoleEnum.ROLE_PATIENT)
                                .build()))
                        .isBlocked(false)
                        .gender(GenderEnum.M)
                        .place(LocationEnum.VELACHERY)
                        .build())
                .isDeleted(false)
                .build();
        tests = List.of(ResponseTestInCartDto.builder()
                        .id(1L)
                        .name("Blood test")
                        .price(100)
                        .description("Simple Blood Test")
                        .build(),
                ResponseTestInCartDto.builder()
                        .id(2L)
                        .name("Cancer Test")
                        .description("Simple Cancer test")
                        .price(300)
                        .build());
        labTests = List.of(LabTest.builder()
                        .id(1L)
                        .name("Blood test")
                        .price(100)
                        .description("Simple Blood Test")
                        .build(),
                LabTest.builder()
                        .id(2L)
                        .name("Cancer Test")
                        .description("Simple Cancer test")
                        .price(300)
                        .build());
        String uuid = UUID.randomUUID().toString();
        user = User.builder()
                .password("admin@123")
                .email("admin@gmail.com")
                .UUID(uuid)
                .roles(List.of(Role.builder()
                                .id(1)
                                .name(RoleEnum.ROLE_ADMIN)
                                .build(),
                        Role.builder()
                                .id(2)
                                .name(RoleEnum.ROLE_SAMPLE_COLLECTOR)
                                .build(),
                        Role.builder()
                                .id(3)
                                .name(RoleEnum.ROLE_PATIENT)
                                .build()))
                .place(LocationEnum.VELACHERY)
                .isBlocked(false)
                .phoneNumber("1234567890")
                .build();
        admin = Admin.builder()
                .user(user)
                .build();
        responseOrderDto = ResponseOrderDto.builder()
                .id(1L)
                .tests(tests)
                .testStatus(TestStatusEnum.IN_PROGRESS)
                .testPackageDto(ResponseTestPackageDto.builder()
                        .labTests(List.of(LabTestDto.builder()
                                        .id(1L)
                                        .name("Blood test")
                                        .price(100)
                                        .description("Simple Blood Test")
                                        .build(),
                                LabTestDto.builder()
                                        .id(2L)
                                        .name("Cancer Test")
                                        .description("Simple Cancer test")
                                        .price(300)
                                        .build()))
                        .id(1L)
                        .name("Test Pack")
                        .price(500)
                        .description("Simple test pack")
                        .build())
                .build();
        slot = AppointmentSlot.builder()
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .timeSlot("7PM")
                .isSampleCollected(false)
                .dateSlot(LocalDate.parse("2024-08-29"))
                .sampleCollector(sampleCollector)
                .build();
        testResult = TestResult.builder()
                .orderDate(LocalDate.parse("2024-08-29"))
                .generatedAt(LocalDateTime.now())
                .ageAndGender("24 M")
                .result(List.of("Good Health Condition"))
                .build();
        order = Order.builder()
                .id(1L)
                .labLocation(LocationEnum.VELACHERY)
                .tests(labTests)
                .patient(patient)
                .testPackage(testPackage)
                .slot(slot)
                .testResult(testResult)
                .build();
    }

    @Test
    void testGetOrders_positive() {
        when(adminService.getAdminByEmail(anyString())).thenReturn(admin);
        when(orderService.getOrdersByLocation(user.getPlace())).thenReturn(List.of(responseOrderDto));
        List<ResponseOrderDto> result = labService.getOrders();
        assertEquals(List.of(responseOrderDto), result);
    }

    @Test
    void testGetOrders_exception() {
        when(adminService.getAdminByEmail(anyString())).thenReturn(admin);
        when(orderService.getOrdersByLocation(user.getPlace())).thenThrow(RuntimeException.class);
        Exception exception = assertThrows(LabException.class,() -> labService.getOrders());
        assertEquals("Error while fetching orders: ", exception.getMessage());
    }

    @Test
    void testAssignReport_positive() {
        when(orderService.getOrder(anyLong())).thenReturn(order);
    }
}