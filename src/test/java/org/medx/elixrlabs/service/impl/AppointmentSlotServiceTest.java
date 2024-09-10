package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.exception.SlotException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.*;
import org.medx.elixrlabs.repository.AppointmentSlotRepository;
import org.medx.elixrlabs.service.*;
import org.medx.elixrlabs.util.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AppointmentSlotServiceTest {

    @Mock
    private AppointmentSlotRepository appointmentSlotRepository;

    @Mock
    private SampleCollectorService sampleCollectorService;

    @Mock
    private CartService cartService;

    @Mock
    private PatientService patientService;

    @Mock
    private EmailService emailService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AppointmentSlotServiceImpl appointmentSlotService;

    private SlotBookDto slotBookDto;
    private RequestSlotBookDto requestSlotBookDto;
    private ResponseCartDto cart;
    private User user;
    private Patient patient;
    private Order order;

    @BeforeEach
    void setUp() {
        requestSlotBookDto = RequestSlotBookDto.builder()
                .date(LocalDate.now())
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .build();
        AppointmentSlot slot = AppointmentSlot.builder()
                .id(1L)
                .timeSlot("7PM")
                .isSampleCollected(false)
                .dateSlot(LocalDate.now())
                .location(LocationEnum.VELACHERY)
                .build();
        TestPackage testPackage = TestPackage.builder()
                .id(1L)
                .description("Simple Tests")
                .name("Test Pack")
                .price(1200)
                .tests(List.of(LabTest.builder()
                        .id(2L)
                        .name("Cancer Test")
                        .defaultValue("Cell Count : 500")
                        .price(1500.00)
                        .build()))
                .build();
        slotBookDto = SlotBookDto.builder()
                .date(LocalDate.now())
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .timeSlot("7PM")
                .build();
        LabTest test = LabTest.builder()
                .id(1L)
                .name("Blood Test")
                .defaultValue("BPC : 100")
                .price(200.00)
                .build();
        user = User.builder()
                .roles(List.of(Role.builder()
                        .name(RoleEnum.ROLE_PATIENT)
                        .id(1)
                        .build()))
                .email("user@gmail.com")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.parse("1999-08-29"))
                .gender(GenderEnum.M)
                .UUID("12345")
                .password("user@123")
                .build();
        patient = Patient.builder()
                .user(user)
                .id(1L)
                .isDeleted(false)
                .build();
        cart = ResponseCartDto.builder()
                .testPackage(ResponseTestPackageDto.builder()
                        .id(1L)
                        .description("Simple Tests")
                        .name("Test Pack")
                        .price(1200)
                        .labTests(List.of(LabTestDto.builder()
                                .id(2L)
                                .name("Cancer Test")
                                .defaultValue("Cell Count : 500")
                                .price(1500.00)
                                .build()))
                        .build())
                .tests(List.of(ResponseTestInCartDto.builder().build()))
                .build();
        order = Order.builder()
                .slot(slot)
                .tests(List.of(test))
                .paymentStatus(PaymentStatusEnum.PAID)
                .patient(patient)
                .sampleCollectionPlace(slotBookDto.getTestCollectionPlace())
                .labLocation(slotBookDto.getLocation())
                .testPackage(testPackage)
                .testStatus(TestStatusEnum.PENDING)
                .price(cart.getPrice())
                .orderDateTime(Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("GMT+05:30"))))
                .build();
    }

    @Test
    void testGetAvailableSlots() {
        when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(slotBookDto.getLocation(), slotBookDto.getTestCollectionPlace(), slotBookDto.getDate())).thenReturn(new ArrayList<>());
        when(sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation())).thenReturn(List.of(SampleCollector.builder().build(), SampleCollector.builder().build()));
        Set<String> availableSlotTimings = appointmentSlotService.getAvailableSlots(requestSlotBookDto);
        assertEquals(availableSlotTimings.size(), 12);
    }

    @Test
    void testGetAvailableSlots_exception() {
        when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(slotBookDto.getLocation(), slotBookDto.getTestCollectionPlace(), slotBookDto.getDate())).thenReturn(null);
        assertThrows(LabException.class, () -> appointmentSlotService.getAvailableSlots(requestSlotBookDto));
    }

    @Test
    void testBookSlot() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(slotBookDto.getLocation(), slotBookDto.getTestCollectionPlace(), slotBookDto.getDate())).thenReturn(new ArrayList<>());
            when(sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation())).thenReturn(List.of(SampleCollector.builder().build(), SampleCollector.builder().build()));
            when(patientService.getPatientByEmail(patient.getUser().getEmail())).thenReturn(patient);
            when(cartService.getCartByPatient()).thenReturn(cart);
            when(orderService.createOrUpdateOrder(any(Order.class))).thenReturn(OrderSuccessDto.builder().id(1).dateTime(LocalDateTime.now()).build());
            OrderSuccessDto orderSuccessDto = appointmentSlotService.bookSlot(slotBookDto);
            assertEquals(orderSuccessDto.getId(), 1);
        }
    }

    @Test
    void testBookSlot_cart_null() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(slotBookDto.getLocation(), slotBookDto.getTestCollectionPlace(), slotBookDto.getDate())).thenReturn(new ArrayList<>());
            when(sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation())).thenReturn(List.of(SampleCollector.builder().build(), SampleCollector.builder().build()));
            when(patientService.getPatientByEmail(patient.getUser().getEmail())).thenReturn(patient);
            when(cartService.getCartByPatient()).thenReturn(ResponseCartDto.builder().tests(new ArrayList<>()).build());
            assertThrows(SlotException.class, () -> appointmentSlotService.bookSlot(slotBookDto));
        }
    }

    @Test
    void testBookSlot_exception() {

        when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(null, slotBookDto.getTestCollectionPlace(), slotBookDto.getDate())).thenReturn(new ArrayList<>());
        when(sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation())).thenReturn(List.of(SampleCollector.builder().build(), SampleCollector.builder().build()));

        when(patientService.getPatientByEmail(patient.getUser().getEmail())).thenReturn(patient);
        when(cartService.getCartByPatient()).thenReturn(cart);
        when(orderService.createOrUpdateOrder(any(Order.class))).thenReturn(OrderSuccessDto.builder().id(1).dateTime(LocalDateTime.now()).build());
        assertThrows(SlotException.class, () -> appointmentSlotService.bookSlot(slotBookDto));
    }

    @Test
    void testBookSlot_exception_slot_filled() {

        when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(LocationEnum.MARINA, slotBookDto.getTestCollectionPlace(), slotBookDto.getDate())).thenReturn(new ArrayList<>(Arrays.asList(AppointmentSlot.builder().timeSlot("7PM").build(), AppointmentSlot.builder().timeSlot("7PM").build(), AppointmentSlot.builder().timeSlot("7PM").build())));
        when(sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation())).thenReturn(List.of(SampleCollector.builder().build(), SampleCollector.builder().build()));

        when(patientService.getPatientByEmail(patient.getUser().getEmail())).thenReturn(patient);
        when(cartService.getCartByPatient()).thenReturn(cart);
        when(orderService.createOrUpdateOrder(any(Order.class))).thenReturn(OrderSuccessDto.builder().id(1).dateTime(LocalDateTime.now()).build());
        assertThrows(SlotException.class, () -> appointmentSlotService.bookSlot(slotBookDto));
    }

    @Test
    void testGetAppointmentsByPlace() {
        when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(LocationEnum.MARINA, TestCollectionPlaceEnum.HOME, slotBookDto.getDate())).thenReturn(new ArrayList<>(Collections.singletonList(AppointmentSlot.builder()
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .location(LocationEnum.MARINA)
                .timeSlot("7AM")
                .isSampleCollected(false)
                .id(1)
                .patient(Patient.builder()
                        .user(User.builder().email("test@gmail.com").build())
                        .build())
                .build())));
        List<AppointmentDto> appointmentDtos = appointmentSlotService.getAppointmentsByPlace(LocationEnum.MARINA, slotBookDto.getDate());
        assertEquals(appointmentDtos.size(), 1);
    }

    @Test
    void testGetAppointmentsByPlace_exception() {
        when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(LocationEnum.MARINA, TestCollectionPlaceEnum.HOME, slotBookDto.getDate())).thenReturn(null);
        assertThrows(LabException.class, () -> appointmentSlotService.getAppointmentsByPlace(LocationEnum.MARINA, slotBookDto.getDate()));
    }

    @Test
    void testAssignSampleCollectorsToAppointment() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn("sc@gmail.com");
            when(appointmentSlotRepository.findById(1L)).thenReturn(Optional.ofNullable(AppointmentSlot.builder().build()));
            appointmentSlotService.assignSampleCollectorToAppointment(1L);
        }
    }

    @Test
    void testAssignSampleCollectorsToAppointment_exception() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(sampleCollectorService.getSampleCollectorByEmail("test@xyz.com")).thenReturn(null);
            when(appointmentSlotRepository.findById(1L)).thenReturn(Optional.ofNullable(AppointmentSlot.builder().build()));
            assertThrows(LabException.class, () -> appointmentSlotService.assignSampleCollectorToAppointment(1L));
        }
    }

    @Test
    void testMarkSampleCollected() {
        when(appointmentSlotRepository.findById(1L)).thenReturn(Optional.ofNullable(AppointmentSlot.builder().build()));
        when(orderService.getOrderByAppointment(AppointmentSlot.builder().isSampleCollected(true).build())).thenReturn(order);
        appointmentSlotService.markSampleCollected(1L);
    }

    @Test
    void testGetAppointmentsBySampleCollector() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(sampleCollectorService.getSampleCollectorByEmail(user.getEmail())).thenReturn(SampleCollector.builder().build());
            when(appointmentSlotRepository.findBySampleCollectorId(any())).thenReturn(List.of(AppointmentSlot.builder()
                    .id(1L)
                    .dateSlot(LocalDate.now())
                    .timeSlot("7AM")
                    .patient(Patient.builder()
                            .user(User.builder().email("test@gmail.com").build())
                            .build())
                    .build()));
            List<AppointmentDto> appointmentDtos = appointmentSlotService.getAppointmentsBySampleCollector();
            assertEquals(appointmentDtos.size(), 1);
        }
    }

    @Test
    void testGetAppointmentsBySampleCollector_appointment_null() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
        when(sampleCollectorService.getSampleCollectorByEmail(user.getEmail())).thenReturn(SampleCollector.builder().build());
        when(appointmentSlotRepository.findBySampleCollectorId(any())).thenReturn(null);
        assertThrows(LabException.class, () -> appointmentSlotService.getAppointmentsBySampleCollector());
        }
    }

    @Test
    void testGetAppointmentsBySampleCollector_exception() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(sampleCollectorService.getSampleCollectorByEmail(user.getEmail())).thenReturn(SampleCollector.builder().build());
            when(appointmentSlotRepository.findBySampleCollectorId(1L)).thenReturn(List.of(AppointmentSlot.builder()
                    .id(1L)
                    .dateSlot(LocalDate.now())
                    .timeSlot("7AM")
                    .patient(Patient.builder()
                            .user(User.builder().email("test@gmail.com").build())
                            .build())
                    .build()));
            assertThrows(LabException.class, () -> appointmentSlotService.getAppointmentsBySampleCollector());
        }
    }

    @Test
    void testGetCollectedAppointmentsBySampleCollector() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(sampleCollectorService.getSampleCollectorByEmail(user.getEmail())).thenReturn(SampleCollector.builder().id(1L).build());
            when(appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedTrue(anyLong())).thenReturn(List.of(AppointmentSlot.builder()
                    .id(1L)
                    .dateSlot(LocalDate.now())
                    .timeSlot("7AM")
                    .patient(Patient.builder()
                            .user(User.builder().email("test@gmail.com").build())
                            .build())
                    .isSampleCollected(true)
                    .build()));
            List<AppointmentDto> appointmentDtos = appointmentSlotService.getCollectedAppointmentsBySampleCollector();
            assertEquals(appointmentDtos.size(), 1);
        }
    }

    @Test
    void testGetCollectedAppointmentsBySampleCollector_appointmets_null() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(sampleCollectorService.getSampleCollectorByEmail(user.getEmail())).thenReturn(SampleCollector.builder().id(1L).build());
            when(appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedTrue(anyLong())).thenReturn(null);
            assertThrows(LabException.class, () -> appointmentSlotService.getCollectedAppointmentsBySampleCollector());
        }
    }

    @Test
    void testGetCollectedAppointmentsBySampleCollector_exception() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(null);
            assertThrows(LabException.class, () -> appointmentSlotService.getCollectedAppointmentsBySampleCollector());
        }
    }

    @Test
    void testGetPendingAppointmentsBySampleCollector() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(sampleCollectorService.getSampleCollectorByEmail(user.getEmail())).thenReturn(SampleCollector.builder().id(1L).build());
            when(appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedFalse(anyLong())).thenReturn(List.of(AppointmentSlot.builder()
                    .id(1L)
                    .dateSlot(LocalDate.now())
                    .timeSlot("7AM")
                    .patient(Patient.builder()
                            .user(User.builder().email("test@gmail.com").build())
                            .build())
                    .build()));
            List<AppointmentDto> appointmentDtos = appointmentSlotService.getPendingAppointmentsBySampleCollector();
            assertEquals(appointmentDtos.size(), 1);
        }
    }

    @Test
    void testGetPendingAppointmentsBySampleCollector_appointment_null() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(sampleCollectorService.getSampleCollectorByEmail(user.getEmail())).thenReturn(SampleCollector.builder().id(1L).build());
            when(appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedFalse(anyLong())).thenReturn(null);
            assertThrows(LabException.class, () -> appointmentSlotService.getPendingAppointmentsBySampleCollector());
        }
    }

    @Test
    void testGetPendingAppointmentsBySampleCollector_exception() {
        try (MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(null);
            assertThrows(LabException.class, () -> appointmentSlotService.getPendingAppointmentsBySampleCollector());
        }
    }

    @Test
    void testSBookSlot_slotFull() {
        List<AppointmentSlot> appointmentSlots = new ArrayList<>(Arrays.asList(AppointmentSlot.builder().timeSlot("7PM").build(), AppointmentSlot.builder().timeSlot("7PM").build()));
        when(appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(slotBookDto.getLocation(), slotBookDto.getTestCollectionPlace(), slotBookDto.getDate())).thenReturn(appointmentSlots);
        when(sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation())).thenReturn(List.of(SampleCollector.builder().build()));
        assertThrows(SlotException.class, () -> appointmentSlotService.bookSlot(slotBookDto));
    }
}
