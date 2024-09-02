package org.medx.elixrlabs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.dto.RequestSlotBookDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.model.*;
import org.medx.elixrlabs.repository.AppointmentSlotRepository;
import org.medx.elixrlabs.service.impl.AppointmentSlotServiceImpl;
import org.medx.elixrlabs.util.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

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
    private OrderService orderService;

    @InjectMocks
    private AppointmentSlotServiceImpl appointmentSlotService;

    private SlotBookDto slotBookDto;
    private RequestSlotBookDto requestSlotBookDto;
    private AppointmentSlot slot;
    private List<AppointmentSlot> slots;
    private LabTest test;
    private ResponseCartDto cart;
    private User user;
    private Patient patient;
    private AppointmentDto appointmentDto;
    private Order order;

    @BeforeEach
    void setUp() {
        requestSlotBookDto = RequestSlotBookDto.builder()
                .date(LocalDate.parse("2024-08-29"))
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .build();
        slot = AppointmentSlot.builder()
                .id(1L)
                .timeSlot("7PM")
                .isSampleCollected(false)
                .dateSlot(LocalDate.parse("2024-08-29"))
                .location(LocationEnum.VELACHERY)
                .build();
        slots = List.of(slot);
        slotBookDto = SlotBookDto.builder()
                .date(LocalDate.parse("2024-08-29"))
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .timeSlot("7PM")
                .build();
        test = LabTest.builder()
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
                .testPackage(TestPackage.builder()
                        .id(1L)
                        .description("Simple Tests")
                        .name("Test Pack")
                        .price(1200)
                        .tests(List.of(test, LabTest.builder()
                                        .id(2L)
                                        .name("Cancer Test")
                                        .defaultValue("Cell Count : 500")
                                        .price(1500.00)
                                        .isDeleted(false)
                                .build()))
                        .isDeleted(false)
                        .build())
                .build();
        order = Order.builder()
                .slot(slot)
                .tests(List.of(test))
                .paymentStatus(PaymentStatusEnum.PAID)
                .patient(patient)
                .sampleCollectionPlace(slotBookDto.getTestCollectionPlace())
                .labLocation(slotBookDto.getLocation())
                .testPackage(cart.getTestPackage())
                .testStatus(TestStatusEnum.PENDING)
                .price(cart.getPrice())
                .orderDateTime(Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("GMT+05:30"))))
                .build();
        appointmentDto = AppointmentDto.builder()
                .appointmentId(1L)
                .appointmentDate(LocalDate.parse("2024-08-29"))
                .timeSlot("7PM")
                .userName("user@gmail.com")
                .build();
    }

//    @Test
//    void testGetAvailableSlots_success() {
//       when(appointmentSlotRepository
//               .findByLocationAndTestCollectionPlaceAndDateSlot(slot));
//    }

    @Test
    void testIsSlotAvailable() {

    }

    @Test
    void testBookSlot() {

    }

    @Test
    void testGetAppointmentsByPlace() {

    }

    @Test
    void testCreateOrUpdateAppointment() {

    }

    @Test
    void testAssignSampleCollectorsToAppointment() {

    }

    @Test
    void testMarkSampleCollected() {

    }

    @Test
    void testGetAppointmentsBySampleCollector() {

    }

    @Test
    void testGetCollectedAppointmentsBySampleCollector() {

    }

    @Test
    void testGetPendingAppointmentsBySampleCollector() {

    }
}
