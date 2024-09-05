package org.medx.elixrlabs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TestStatusEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private CartService cartService;

    @Mock
    private AppointmentSlotService appointmentSlotService;

    @InjectMocks
    private PatientController patientController;

    private UserDto userDto;
    private ResponsePatientDto responsePatientDto;
    private RequestSlotBookDto requestSlotBookDto;
    private SlotBookDto slotBookDto;
    private TestResultDto testResultDto;
    private ResponseTestInCartDto test;
    private List<ResponseTestInCartDto> tests;
    private CartDto cartDto;
    private ResponseCartDto responseCartDto;
    private ResponseOrderDto order;
    private List<ResponseOrderDto> orders;
    private OrderSuccessDto orderDto;

    @BeforeEach
    void setUp() {
        UserDto userDto = UserDto.builder()
                .email("user@gmail.com")
                .place(LocationEnum.VELACHERY)
                .password("user@123")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(1999, 2, 25))
                .gender(GenderEnum.M)
                .build();
        responsePatientDto = ResponsePatientDto.builder()
                .id(1L)
                .dateOfBirth(LocalDate.of(1999, 2, 25))
                .email("user@gmail.com")
                .gender(GenderEnum.M)
                .phoneNumber("1234567890")
                .place(LocationEnum.VELACHERY)
                .build();
        requestSlotBookDto = RequestSlotBookDto.builder()
                .date(LocalDate.parse("2024-09-01"))
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .build();
        slotBookDto = SlotBookDto.builder()
                .timeSlot("7PM")
                .date(LocalDate.parse("2024-09-01"))
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .build();
        testResultDto = TestResultDto.builder()
                .generatedAt(LocalDateTime.now())
                .id(1L)
                .email("user@gmail.com")
                .orderDate(LocalDate.of(2024, 8, 29))
                .result(List.of("Normal"))
                .ageAndGender("24 M")
                .build();
        test = ResponseTestInCartDto.builder()
                .id(1L)
                .name("Blood Test")
                .description("Simple Blood Test")
                .price(200.00)
                .build();
        tests = List.of(test);
        cartDto = CartDto.builder()
                .testIds(List.of(test.getId()))
                .testPackageId(null)
                .build();
        responseCartDto = ResponseCartDto.builder()
                .id(1L)
                .tests(tests)
                .price(200.00)
                .testPackage(null)
                .build();
        order = ResponseOrderDto.builder()
                .testStatus(TestStatusEnum.IN_PROGRESS)
                .tests(tests)
                .id(1L)
                .build();
        orders = List.of(order, order);
        orderDto = OrderSuccessDto.builder()
                .dateTime(testResultDto.getGeneratedAt())
                .id(1L)
                .build();
    }

    @Test
    void testCreateOrUpdatePatient() {
        when(patientService.createOrUpdatePatient(userDto)).thenReturn(responsePatientDto);
        ResponseEntity<ResponsePatientDto> result = patientController.createPatient(userDto);
        assertEquals(responsePatientDto, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testGetAvailableSlots() {
        when(appointmentSlotService.getAvailableSlots(any(RequestSlotBookDto.class))).thenReturn(Set.of("1AM", "2AM"));
        ResponseEntity<Set<String>> result = patientController.getAvailableSlots(requestSlotBookDto);
        assertEquals(Set.of("1AM", "2AM"), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testBookSlot() {
        when(appointmentSlotService.bookSlot(any(SlotBookDto.class))).thenReturn(orderDto);
        ResponseEntity<OrderSuccessDto> result = patientController.bookSlot(slotBookDto);
        assertEquals(orderDto, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdatePatient() {
        when(patientService.createOrUpdatePatient(userDto)).thenReturn(responsePatientDto);
        ResponseEntity<ResponsePatientDto> result = patientController.updatePatient(userDto);
        assertEquals(responsePatientDto, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeletePatient() {
        doNothing().when(patientService).deletePatient();
        ResponseEntity<HttpStatus.Series> result = patientController.deletePatient();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testAddTestsOrPackageToCart() {
        when(cartService.addTestsOrPackagesToCart(any(CartDto.class))).thenReturn(responseCartDto);
        ResponseEntity<ResponseCartDto> result = patientController.addTestOrPackagesToCart(cartDto);
        assertEquals(responseCartDto, result.getBody());
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void testGetCart() {
        when(cartService.getCartByPatient()).thenReturn(responseCartDto);
        ResponseEntity<ResponseCartDto> result = patientController.getCart();
        assertEquals(responseCartDto, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testRemoveTestsOrPackagesFromCart() {
        doNothing().when(cartService).deleteCart();
        ResponseEntity<HttpStatus.Series> result = patientController.removeTestsOrPackageFromCart();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
